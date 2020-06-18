package com.butenkos.country.info.provider.dao.gateway;

import com.butenkos.country.info.provider.dao.entity.CountryData;

import java.util.List;

public interface CountryDataGateway {
  List<CountryData> getData();

  List<CountryData> getData(int page, int itemsPerPage);
}
