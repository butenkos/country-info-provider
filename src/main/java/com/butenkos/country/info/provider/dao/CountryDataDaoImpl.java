package com.butenkos.country.info.provider.dao;

import com.butenkos.country.info.provider.dao.entity.CountryData;
import com.butenkos.country.info.provider.dao.gateway.CountryDataGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CountryDataDaoImpl implements CountryDataDao {
  private final CountryDataGateway gateway;
  /**
   * number of non-country items (regions, continents etc)
   */
  private final int itemsToSkip;

  @Autowired
  public CountryDataDaoImpl(CountryDataGateway gateway, @Value("${results.to.skip}") int itemsToSkip) {
    this.gateway = gateway;
    this.itemsToSkip = itemsToSkip;
  }

  @Override
  public List<CountryData> getData() {
    return gateway.getData().stream()
        .skip(itemsToSkip)
        .collect(Collectors.toList());
  }
}
