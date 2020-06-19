package com.butenkos.country.info.provider.model.response;

import java.util.Collections;
import java.util.List;

/**
 * response model
 */
public class CountryDataResponse {
  private final List<CountryDataViewModel> countryDataList;

  public CountryDataResponse(List<CountryDataViewModel> countryDataList) {
    this.countryDataList = Collections.unmodifiableList(countryDataList);
  }

  public List<CountryDataViewModel> getCountryDataList() {
    return countryDataList;
  }
}
