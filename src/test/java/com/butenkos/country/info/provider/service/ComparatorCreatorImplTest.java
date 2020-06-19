package com.butenkos.country.info.provider.service;

import com.butenkos.country.info.provider.gateway.entity.CountryData;
import com.butenkos.country.info.provider.helper.DummyCountryDataSupplier;
import com.butenkos.country.info.provider.model.request.SortingCriterion;
import com.butenkos.country.info.provider.model.request.SortingField;
import com.butenkos.country.info.provider.model.request.SortingOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ComparatorCreatorImplTest {
  private static ComparatorCreator creator;
  private static DummyCountryDataSupplier dataSupplier;

  @BeforeAll
  public static void setup() {
    creator = new ComparatorCreatorImpl();
    dataSupplier = new DummyCountryDataSupplier();
  }

  @Test
  public void nullNotAllowed() {
    assertThrows(IllegalArgumentException.class, () -> creator.createCombinedComparator(null));
  }

  @Test
  public void whenEmptyListPassedThenEmptyOptionalReturned() {
    assertFalse(creator.createCombinedComparator(Collections.emptyList()).isPresent());
  }

  @Test
  public void canSortInAscendingOrder() {
    final Comparator<CountryData> combinedComparator = creator.createCombinedComparator(
        Collections.singletonList(new SortingCriterion(SortingField.POPULATION, SortingOrder.ASC))
    ).orElseThrow(IllegalArgumentException::new);
    final List<CountryData> sortedWithCombinedComparator = dataSupplier.prepareTestData().stream()
        .sorted(combinedComparator)
        .collect(Collectors.toList());
    final List<CountryData> sortedByPopulationAsc = dataSupplier.prepareTestData().stream()
        .sorted(Comparator.comparingLong(CountryData::getValue))
        .collect(Collectors.toList());
    assertNotSame(sortedByPopulationAsc, sortedWithCombinedComparator);
    assertEquals(sortedByPopulationAsc, sortedWithCombinedComparator);
  }

  @Test
  public void canSortInDescendingOrder() {
    final Comparator<CountryData> combinedComparator = creator.createCombinedComparator(
        Collections.singletonList(new SortingCriterion(SortingField.POPULATION, SortingOrder.DESC))
    ).orElseThrow(IllegalArgumentException::new);
    final List<CountryData> sortedWithCombinedComparator = dataSupplier.prepareTestData().stream()
        .sorted(combinedComparator)
        .collect(Collectors.toList());
    final List<CountryData> sortedByPopulationDesc = dataSupplier.prepareTestData().stream()
        .sorted(Comparator.comparingLong(CountryData::getValue).reversed())
        .collect(Collectors.toList());
    assertNotSame(sortedByPopulationDesc, sortedWithCombinedComparator);
    assertEquals(sortedByPopulationDesc, sortedWithCombinedComparator);
  }

  @Test
  public void canSortByMultipleFields() {
    final Comparator<CountryData> combinedComparator = creator.createCombinedComparator(
        Arrays.asList(
            new SortingCriterion(SortingField.POPULATION, SortingOrder.DESC),
            new SortingCriterion(SortingField.COUNTRY_NAME, SortingOrder.DESC)
        )
    ).orElseThrow(IllegalArgumentException::new);
    final List<CountryData> sortedWithCombinedComparator = dataSupplier.prepareTestData().stream()
        .sorted(combinedComparator)
        .collect(Collectors.toList());
    final List<CountryData> sortedByPopulationDescAndCountryNameDesc = dataSupplier.prepareTestData().stream()
        .sorted(
            Comparator.comparingLong(CountryData::getValue).reversed()
                .thenComparing(countryData -> countryData.getCountry().getValue(), Comparator.reverseOrder())
        ).collect(Collectors.toList());
    assertNotSame(sortedByPopulationDescAndCountryNameDesc, sortedWithCombinedComparator);
    assertEquals(sortedByPopulationDescAndCountryNameDesc, sortedWithCombinedComparator);
  }

  @Test
  public void canSortByMultipleFieldsInDifferentOrder() {
    final Comparator<CountryData> combinedComparator = creator.createCombinedComparator(
        Arrays.asList(
            new SortingCriterion(SortingField.POPULATION, SortingOrder.ASC),
            new SortingCriterion(SortingField.COUNTRY_NAME, SortingOrder.DESC)
        )
    ).orElseThrow(IllegalArgumentException::new);
    final List<CountryData> sortedWithCombinedComparator = dataSupplier.prepareTestData().stream()
        .sorted(combinedComparator)
        .collect(Collectors.toList());
    final List<CountryData> sortedByPopulationAscAndCountryNameDesc = dataSupplier.prepareTestData().stream()
        .sorted(
            Comparator.comparingLong(CountryData::getValue)
                .thenComparing(countryData -> countryData.getCountry().getValue(), Comparator.reverseOrder())
        ).collect(Collectors.toList());
    assertNotSame(sortedByPopulationAscAndCountryNameDesc, sortedWithCombinedComparator);
    assertEquals(sortedByPopulationAscAndCountryNameDesc, sortedWithCombinedComparator);
  }
}