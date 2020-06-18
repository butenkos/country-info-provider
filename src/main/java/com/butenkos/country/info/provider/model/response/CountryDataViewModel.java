package com.butenkos.country.info.provider.model.response;

import com.butenkos.country.info.provider.dao.entity.CountryData;

public class CountryDataViewModel {
  private final CountryData countryData;

  public CountryDataViewModel(CountryData countryData) {
    this.countryData = countryData;
  }

  public String getCountryId() {
    return countryData.getCountry().getId();
  }

  public String getCountryName() {
    return countryData.getCountry().getValue();
  }

  public String getIso3Code() {
    return countryData.getCountryIso3Code();
  }

  public Long getPopulation() {
    return countryData.getValue();
  }

  public int getYear() {
    return countryData.getDate();
  }
}
