package com.butenkos.country.info.provider.model.request;

import com.butenkos.country.info.provider.model.request.StringToSortingCriteriaConverter.FailedToCreateSortingCriterionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StringToSortingCriteriaConverterTest {
  private static Converter<String, List<SortingCriterion>> converter;

  @BeforeAll
  public static void setup() {
    converter = new StringToSortingCriteriaConverter();
  }

  @Test
  public void failsOnInvalidString() {
    assertThrows(FailedToCreateSortingCriterionException.class, () -> converter.convert(""));
    assertThrows(FailedToCreateSortingCriterionException.class, () -> converter.convert("-"));
    assertThrows(FailedToCreateSortingCriterionException.class, () -> converter.convert("unknown"));
  }

  @Test
  public void convertsToSortingCriterionIgnoringCase() {
    final SortingCriterion criterion = new SortingCriterion(SortingField.POPULATION, SortingOrder.ASC);
    final List<SortingCriterion> criteriaFromString = Optional.ofNullable(
        converter.convert("pOpUlAtIoN")
    ).orElseThrow(IllegalArgumentException::new);
    assertFalse(criteriaFromString.isEmpty());
    final SortingCriterion criterionFromString = criteriaFromString.get(0);
    assertEquals(criterion.getField(), criterionFromString.getField());
    assertEquals(criterion.getOrder(), criterionFromString.getOrder());
  }

  @Test
  public void whenNoMinusSignBeforeFieldNameThenOrderIsAscending() {
    final SortingCriterion criterionFromString = createCriterionFromString("country_code");
    assertEquals(SortingField.COUNTRY_CODE, criterionFromString.getField());
    assertEquals(SortingOrder.ASC, criterionFromString.getOrder());
  }

  @Test
  public void whenMinusSignBeforeFieldNameThenOrderIsDescending() {
    final SortingCriterion criterionFromString = createCriterionFromString("-COUNTRY_NAME");
    assertEquals(SortingField.COUNTRY_NAME, criterionFromString.getField());
    assertEquals(SortingOrder.DESC, criterionFromString.getOrder());
  }

  private SortingCriterion createCriterionFromString(String fieldName) {
    final List<SortingCriterion> criteria = Optional.ofNullable(
        converter.convert(fieldName)
    ).orElseThrow(IllegalArgumentException::new);
    if (!criteria.isEmpty()) {
      return criteria.get(0);
    } else {
      throw new IllegalArgumentException();
    }
  }
}