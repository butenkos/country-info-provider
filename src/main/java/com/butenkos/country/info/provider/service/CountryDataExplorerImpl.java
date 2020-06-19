package com.butenkos.country.info.provider.service;

import com.butenkos.country.info.provider.gateway.CountryDataGateway;
import com.butenkos.country.info.provider.gateway.entity.CountryData;
import com.butenkos.country.info.provider.model.request.FilteringCriteria;
import com.butenkos.country.info.provider.model.request.SortingCriterion;
import com.butenkos.country.info.provider.model.request.SortingField;
import com.butenkos.country.info.provider.model.request.SortingOrder;
import com.butenkos.country.info.provider.model.response.CountryDataResponse;
import com.butenkos.country.info.provider.model.response.CountryDataViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CountryDataExplorerImpl implements CountryDataExplorer {
  private static final Logger LOG = LoggerFactory.getLogger(CountryDataExplorerImpl.class);
  private final List<SortingCriterion> defaultSortingCriteria;
  private final CountryDataGateway gateway;
  private final ComparatorCreator comparatorCreator;
  private final int defaultDisplayLimit;

  @Autowired
  public CountryDataExplorerImpl(
      CountryDataGateway gateway,
      ComparatorCreator comparatorCreator,
      @Value("${display.country.limit.default}") int defaultDisplayLimit,
      @Value("${sorting.criterion.default.field}") String defaultSortingField,
      @Value("${sorting.criterion.default.order}") String defaultSortingOrder
  ) {
    this.gateway = gateway;
    this.comparatorCreator = comparatorCreator;
    this.defaultDisplayLimit = defaultDisplayLimit;
    defaultSortingCriteria = Collections.singletonList(
        new SortingCriterion(
            SortingField.valueOf(defaultSortingField),
            SortingOrder.valueOf(defaultSortingOrder)
        )
    );
  }

  @Override
  public CountryDataResponse getCountryData(
      List<SortingCriterion> sortingCriteria,
      FilteringCriteria filteringCriteria
  ) {
    LOG.debug("new request: sortingCriteria={}, filteringCriteria={}", sortingCriteria, filteringCriteria);
    Stream<CountryData> stream = gateway.getData().stream();
    final Optional<Comparator<CountryData>> comparator = comparatorCreator.createCombinedComparator(
        sortingCriteria == null ? defaultSortingCriteria : sortingCriteria
    );
    if (comparator.isPresent()) {
      stream = stream.sorted(comparator.get());
    }
    final int limit = Optional.ofNullable(filteringCriteria)
        .orElse(new FilteringCriteria(defaultDisplayLimit))
        .getNumberOfCountriesToShow()
        .orElse(defaultDisplayLimit);
    if (limit > 0) {
      stream = stream.limit(limit);
    }
    return new CountryDataResponse(stream.map(CountryDataViewModel::new).collect(Collectors.toList()));
  }
}
