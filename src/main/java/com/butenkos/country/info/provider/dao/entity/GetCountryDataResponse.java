package com.butenkos.country.info.provider.dao.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GetCountryDataResponse {
  public final CountryDataTotals totals;
  public final List<CountryData> countryDataList;

  public GetCountryDataResponse(CountryDataTotals totals, List<CountryData> countryDataList) {
    this.totals = Optional.ofNullable(totals).orElseThrow(RuntimeException::new); //TODO
    this.countryDataList = Collections.unmodifiableList(countryDataList == null ? new ArrayList<>() : countryDataList);
  }
}
