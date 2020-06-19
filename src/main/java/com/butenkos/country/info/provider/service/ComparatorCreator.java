package com.butenkos.country.info.provider.service;

import com.butenkos.country.info.provider.gateway.entity.CountryData;
import com.butenkos.country.info.provider.model.request.SortingCriterion;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of this interface is responsible for creating of a combined Comparator
 * which chains together all Comparators created for every SortingCriterion
 *
 * @see SortingCriterion
 */
public interface ComparatorCreator {
  Optional<Comparator<CountryData>> createCombinedComparator(List<SortingCriterion> sortingCriteria);
}
