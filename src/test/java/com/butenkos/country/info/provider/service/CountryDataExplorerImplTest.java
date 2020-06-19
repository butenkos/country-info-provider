package com.butenkos.country.info.provider.service;

import com.butenkos.country.info.provider.gateway.entity.CountryData;
import com.butenkos.country.info.provider.helper.DummyCountryDataSupplier;
import com.butenkos.country.info.provider.model.request.FilteringCriteria;
import com.butenkos.country.info.provider.model.request.SortingCriterion;
import com.butenkos.country.info.provider.model.request.SortingField;
import com.butenkos.country.info.provider.model.request.SortingOrder;
import com.butenkos.country.info.provider.model.response.CountryDataResponse;
import com.butenkos.country.info.provider.model.response.CountryDataViewModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryDataExplorerImplTest {
  private static final int DEFAULT_DISPLAY_LIMIT = 3;
  private static CountryDataExplorer explorer;
  private static DummyCountryDataSupplier dataSupplier;

  @BeforeAll
  public static void setup() {
    dataSupplier = new DummyCountryDataSupplier();
    explorer = new CountryDataExplorerImpl(
        () -> dataSupplier.prepareTestData(),
        new ComparatorCreatorImpl(),
        DEFAULT_DISPLAY_LIMIT,
        "POPULATION",
        "ASC"
    );
  }

  @Test
  public void whenNoSortingCriteriaAndFilteringCriteriaProvidedThenDefaultsUsed() {
    final CountryDataResponse countryData = explorer.getCountryData(null, null);
    final List<CountryDataViewModel> dataSortedByPopulationAsc = dataSupplier.prepareTestData().stream()
        .sorted(Comparator.comparingLong(CountryData::getValue))
        .map(CountryDataViewModel::new)
        .collect(Collectors.toList())
        .subList(0, DEFAULT_DISPLAY_LIMIT);
    assertEquals(DEFAULT_DISPLAY_LIMIT, countryData.getCountryDataList().size());
    assertEquals(countryData.getCountryDataList(), dataSortedByPopulationAsc);
  }

  @Test
  public void testSortingCriteriaProvided() {
    final CountryDataResponse countryData = explorer.getCountryData(
        Collections.singletonList(new SortingCriterion(SortingField.COUNTRY_NAME, SortingOrder.DESC)),
        null
    );
    final List<CountryDataViewModel> dataSortedByPopulationAsc = dataSupplier.prepareTestData().stream()
        .sorted(Comparator.comparing(data -> data.getCountry().getValue(), Comparator.reverseOrder()))
        .map(CountryDataViewModel::new)
        .collect(Collectors.toList())
        .subList(0, DEFAULT_DISPLAY_LIMIT);
    assertEquals(DEFAULT_DISPLAY_LIMIT, countryData.getCountryDataList().size());
    assertEquals(countryData.getCountryDataList(), dataSortedByPopulationAsc);
  }

  @Test
  public void testSortingCriteriaAndFilteringCriteriaProvided() {
    final int itemsToDisplay = 5;
    final CountryDataResponse countryData = explorer.getCountryData(
        Collections.singletonList(new SortingCriterion(SortingField.COUNTRY_CODE, SortingOrder.ASC)),
        new FilteringCriteria(itemsToDisplay)
    );
    final List<CountryDataViewModel> dataSortedByPopulationAsc = dataSupplier.prepareTestData().stream()
        .sorted(Comparator.comparing(data -> data.getCountry().getId()))
        .map(CountryDataViewModel::new)
        .collect(Collectors.toList())
        .subList(0, itemsToDisplay);
    assertEquals(itemsToDisplay, countryData.getCountryDataList().size());
    assertEquals(countryData.getCountryDataList(), dataSortedByPopulationAsc);
  }
}