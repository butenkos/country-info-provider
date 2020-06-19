package com.butenkos.country.info.provider.model.request;

import java.util.Optional;

/**
 * data structure for keeping parameters which can be used for filtering of the data
 * at the application side or at remote server (as query parameters).
 * Can be extended with new fields if needed.
 */
public class FilteringCriteria {
  private final Integer numberOfCountriesToShow;

  public FilteringCriteria(Integer numberOfCountriesToShow) {
    this.numberOfCountriesToShow = numberOfCountriesToShow;
  }

  public Optional<Integer> getNumberOfCountriesToShow() {
    return Optional.ofNullable(numberOfCountriesToShow);
  }

  @Override
  public String toString() {
    return "FilteringCriteria{" +
        "numberOfCountriesToShow=" + numberOfCountriesToShow +
        '}';
  }
}
