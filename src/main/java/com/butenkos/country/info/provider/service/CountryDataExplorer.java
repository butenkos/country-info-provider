package com.butenkos.country.info.provider.service;

import com.butenkos.country.info.provider.model.request.SortingCriterion;
import com.butenkos.country.info.provider.model.response.CountryDataResponse;

import java.util.List;

public interface CountryDataExplorer {
  CountryDataResponse foo(List<SortingCriterion> sortingCriteria, int limit);
}
