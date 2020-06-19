package com.butenkos.country.info.provider.model.request;

/**
 * Data structure which holds the name of the field to be sorted by and information about
 * the order of sorting (ascending or descending)
 */
public class SortingCriterion {
  private final SortingField field;
  private final SortingOrder order;

  public SortingCriterion(SortingField field, SortingOrder order) {
    this.field = field;
    this.order = order;
  }

  public SortingField getField() {
    return field;
  }

  public SortingOrder getOrder() {
    return order;
  }

  @Override
  public String toString() {
    return "SortingCriteria{" +
        "field=" + field +
        ", order=" + order +
        '}';
  }
}
