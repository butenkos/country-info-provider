package com.butenkos.country.info.provider.gateway;

import com.butenkos.country.info.provider.gateway.entity.CountryData;

import java.util.List;

public interface CountryDataGateway {
  List<CountryData> getData();
}
