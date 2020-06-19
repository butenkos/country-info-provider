package com.butenkos.country.info.provider.service;

import com.butenkos.country.info.provider.model.request.FilteringCriteria;
import com.butenkos.country.info.provider.model.request.SortingCriterion;
import com.butenkos.country.info.provider.model.response.CountryDataResponse;

import java.util.List;

public interface CountryDataExplorer {
  CountryDataResponse getCountryData(List<SortingCriterion> sortingCriteria, FilteringCriteria filteringCriteria);
}
