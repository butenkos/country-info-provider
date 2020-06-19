package com.butenkos.country.info.provider.model.request;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.butenkos.country.info.provider.model.request.SortingOrder.ASC;
import static com.butenkos.country.info.provider.model.request.SortingOrder.DESC;

/**
 * This class allows to convert query parameter 'sort' to list of {@code SortingCriterion}
 */
@Component
public class StringToSortingCriteriaConverter implements Converter<String, List<SortingCriterion>> {

  @Override
  public List<SortingCriterion> convert(String source) {
    return Arrays.stream(source.split(","))
        .map(this::getSortingCriteria)
        .collect(Collectors.toList());
  }

  private SortingCriterion getSortingCriteria(String string) {
    try {
      final SortingOrder order = string.charAt(0) == '-' ? DESC : ASC;
      final int startIndex = string.charAt(0) == '-' ? 1 : 0;
      final SortingField field = SortingField.valueOf(string.substring(startIndex).toUpperCase());
      return new SortingCriterion(field, order);
    } catch (Exception e) {
      throw new FailedToCreateSortingCriterionException("invalid string: " + string, e);
    }
  }

  static class FailedToCreateSortingCriterionException extends RuntimeException {
    FailedToCreateSortingCriterionException(String message, Exception cause) {
      super(message, cause);
    }
  }
}
