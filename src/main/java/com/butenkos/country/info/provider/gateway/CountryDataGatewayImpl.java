package com.butenkos.country.info.provider.gateway;

import com.butenkos.country.info.provider.gateway.entity.CountryData;
import com.butenkos.country.info.provider.gateway.entity.CountryDataStats;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CountryDataGatewayImpl implements CountryDataGateway {
  private static final Logger LOG = LoggerFactory.getLogger(CountryDataGatewayImpl.class);
  private static final int COUNTRY_DATA_STATS_INDEX = 0;
  private static final int COUNTRY_DATA_ARRAY_INDEX = 1;
  private final WebClient webClient;
  private final ObjectMapper objectMapper;
  private final Map<String, String> defaultQueryParams;
  private final int maxCountriesPerRequest;
  /**
   * number of non-country items (regions, continents etc)
   */
  private final int itemsToSkip;
  private final long timeToWaitResponse;

  @Autowired
  public CountryDataGatewayImpl(
      @Value("#{${data.source.url}}") String url,
      ObjectMapper objectMapper,
      @Value("#{${query.param.default}}") Map<String, String> defaultQueryParams,
      @Value("${query.max.countries.per.request}") int maxCountriesPerRequest,
      @Value("${results.to.skip}") int itemsToSkip,
      @Value("${query.timeout}")long timeToWaitResponse
  ) {
    webClient = WebClient.create(url);
    this.objectMapper = objectMapper;
    this.defaultQueryParams = Collections.unmodifiableMap(defaultQueryParams);
    this.maxCountriesPerRequest = maxCountriesPerRequest;
    this.itemsToSkip = itemsToSkip;
    this.timeToWaitResponse = timeToWaitResponse;
  }


  @Override
  public List<CountryData> getData() {
    //weird structure of the response (an array containing objects of two different types)
    //forces me to do dirty things here...
    final List<Object> response = sendRequestAndGetResponse().orElseThrow(FailedToGetResponseException::new);
    final CountryDataStats countryDataStats = getCountryDataStatsFromResponse(response);
    return getCountryDataList(response, countryDataStats);
  }

  private List<CountryData> getCountryDataList(List<Object> response, CountryDataStats countryDataStats) {
    if (countryDataStats.getPage() < countryDataStats.getPages()) {
      return getFullListOfCountriesFromRemoteSource(response.get(COUNTRY_DATA_ARRAY_INDEX), countryDataStats).stream()
          .skip(itemsToSkip)
          .collect(Collectors.toList());
    } else {
      return convertToCountryDataList(response.get(COUNTRY_DATA_ARRAY_INDEX))
          .stream()
          .skip(itemsToSkip)
          .collect(Collectors.toList());
    }
  }

  private List<CountryData> convertToCountryDataList(Object source) {
    try {
      return objectMapper.convertValue(source, new TypeReference<List<CountryData>>() {
      });
    } catch (Exception e) {
      throw new FailedToGetCountryDataListFromResponseException(e);
    }
  }

  private CountryDataStats getCountryDataStatsFromResponse(List<Object> response) {
    try {
      return objectMapper.convertValue(response.get(COUNTRY_DATA_STATS_INDEX), CountryDataStats.class);
    } catch (Exception e) {
      throw new FailedToGetCountryDataStatsFromResponseException(e);
    }
  }

  private List<CountryData> getFullListOfCountriesFromRemoteSource(Object firstPageResult, CountryDataStats stats) {
    final List<CountryData> countryData = convertToCountryDataList(firstPageResult);
    for (int page = stats.getPage() + 1; page <= stats.getPages(); page++) {
      final List<Object> response = sendRequestAndGetResponse(page).orElseThrow(FailedToGetResponseException::new);
      countryData.addAll(convertToCountryDataList(response.get(COUNTRY_DATA_ARRAY_INDEX)));
    }
    return countryData;
  }

  private Optional<List<Object>> sendRequestAndGetResponse() {
    return sendRequestAndGetResponse(1);
  }

  private Optional<List<Object>> sendRequestAndGetResponse(int page) {
    LOG.debug("trying to get data for page {}", page);
    return webClient.get()
        .uri(uriBuilder -> uriBuilder.queryParams(prepareQueryParams(page)).build())
        .retrieve().bodyToMono(new ParameterizedTypeReference<List<Object>>() {
        }).blockOptional(Duration.ofSeconds(timeToWaitResponse));
  }

  private MultiValueMap<String, String> prepareQueryParams(int page) {
    final MultiValueMap<String, String> propsMap = new LinkedMultiValueMap<>();
    defaultQueryParams.forEach(propsMap::add);
    propsMap.add("per_page", "" + maxCountriesPerRequest);
    propsMap.add("page", "" + page);
    LOG.debug("request query properties: {}", propsMap);
    return propsMap;
  }

  private static class FailedToGetCountryDataListFromResponseException extends RuntimeException {
    public FailedToGetCountryDataListFromResponseException(Exception cause) {
      super(cause);
    }
  }

  private static class FailedToGetCountryDataStatsFromResponseException extends RuntimeException {
    public FailedToGetCountryDataStatsFromResponseException(Exception cause) {
      super(cause);
    }
  }

  private static class FailedToGetResponseException extends RuntimeException {
    public FailedToGetResponseException() {
      super("the response is null");
    }
  }
}
