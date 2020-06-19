package com.butenkos.country.info.provider.helper;

import com.butenkos.country.info.provider.gateway.entity.Country;
import com.butenkos.country.info.provider.gateway.entity.CountryData;

import java.util.Arrays;
import java.util.List;

public class DummyCountryDataSupplier {
  public List<CountryData> prepareTestData() {
    return Arrays.asList(
        createCountryData("AAA", "AAA", 2),
        createCountryData("CCC", "AAA", 4),
        createCountryData("AAA", "BBB", 6),
        createCountryData("BBB", "AAA", 6),
        createCountryData("AAA", "CCC", 2),
        createCountryData("CCC", "AAA", 4)
    );
  }

  private CountryData createCountryData(String name, String code, int population) {
    final CountryData countryData = new CountryData();
    countryData.setCountry(new Country());
    countryData.getCountry().setValue(name);
    countryData.getCountry().setId(code);
    countryData.setValue(population);
    return countryData;
  }
}
