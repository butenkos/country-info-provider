package com.butenkos.country.info.provider.dao.gateway;

import com.butenkos.country.info.provider.dao.entity.CountryData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class CountryDataGatewayImpl implements CountryDataGateway {
  private final WebClient webClient;
  private final ObjectMapper objectMapper;
  private final Map<String, String> defaultQueryParams;
  private final int maxCountriesPerRequest;

  @Autowired
  public CountryDataGatewayImpl(
      @Value("#{${data.source.url}}") String url,
      ObjectMapper objectMapper,
      @Value("#{${query.param.default}}") Map<String, String> defaultQueryParams,
      @Value("${query.max.countries.per.request}") int maxCountriesPerRequest
  ) {
    webClient = WebClient.create(url);
    this.objectMapper = objectMapper;
    this.defaultQueryParams = Collections.unmodifiableMap(defaultQueryParams);
    this.maxCountriesPerRequest = maxCountriesPerRequest;
  }


  @Override
  public List<CountryData> getData() {
    final MultiValueMap<String, String> propsMap = new LinkedMultiValueMap<>();
    defaultQueryParams.forEach(propsMap::add);
    propsMap.add("per_page", "" + maxCountriesPerRequest);
    final List<Object> block = webClient.get()
        .uri(b -> b.queryParams(propsMap).build())
        .retrieve().bodyToMono(new ParameterizedTypeReference<List<Object>>() {
        }).block();
    System.out.println(block);

    return objectMapper.convertValue(block.get(1), new TypeReference<List<CountryData>>() {
    });
  }

  @Override
  public List<CountryData> getData(int page, int itemsPerPage) {
    return null;
  }
}
