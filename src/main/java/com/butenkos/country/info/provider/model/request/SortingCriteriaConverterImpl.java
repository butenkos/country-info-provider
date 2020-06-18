//package com.butenkos.country.info.provider.model.request;
//
//import com.butenkos.country.info.provider.util.NullChecker;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.butenkos.country.info.provider.model.request.SortingOrder.ASC;
//import static com.butenkos.country.info.provider.model.request.SortingOrder.DESC;
//
//public class SortingCriteriaConverterImpl implements SortingCriteriaConverter {
//  @Override
//  public List<SortingCriterion> fromString(String string) {
//    checkStringNotNullOrEmpty(string);
//    return Arrays.stream(string.split(","))
//        .map(this::getSortingCriteria)
//        .collect(Collectors.toList());
//  }
//
//  private SortingCriterion getSortingCriteria(String string) {
//    try {
//      final SortingOrder order = string.charAt(0) == '-' ? DESC : ASC;
//      final int startIndex = string.charAt(0) == '-' ? 1 : 0;
//      final SortingField field = SortingField.valueOf(string.substring(startIndex).toUpperCase());
//      return new SortingCriterion(field, order);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  private void checkStringNotNullOrEmpty(String string) {
//    NullChecker.checkNotNull(string);
//    if (string.trim().isEmpty()) {
//      throw new RuntimeException(); //TODO
//    }
//  }
//}
