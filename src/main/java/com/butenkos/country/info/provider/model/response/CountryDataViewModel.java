package com.butenkos.country.info.provider.model.response;

import com.butenkos.country.info.provider.gateway.entity.CountryData;

import java.util.Objects;

@SuppressWarnings("unused")
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CountryDataViewModel that = (CountryDataViewModel) o;
    return Objects.equals(countryData, that.countryData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(countryData);
  }
}
