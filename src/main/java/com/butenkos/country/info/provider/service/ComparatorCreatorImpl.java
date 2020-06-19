package com.butenkos.country.info.provider.service;

import com.butenkos.country.info.provider.gateway.entity.CountryData;
import com.butenkos.country.info.provider.model.request.SortingCriterion;
import com.butenkos.country.info.provider.model.request.SortingOrder;
import com.butenkos.country.info.provider.util.NullChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @see com.butenkos.country.info.provider.service.ComparatorCreator
 */
@Component
public class ComparatorCreatorImpl implements ComparatorCreator {
  private static final Logger LOG = LoggerFactory.getLogger(ComparatorCreatorImpl.class);

  @Override
  public Optional<Comparator<CountryData>> createCombinedComparator(List<SortingCriterion> sortingCriteria) {
    NullChecker.checkNotNull(sortingCriteria);
    return sortingCriteria.stream()
        .map(this::createComparator)
        .reduce(Comparator::thenComparing);
  }

  private Comparator<CountryData> createComparator(SortingCriterion criterion) {
    Comparator<CountryData> comparator;
    switch (criterion.getField()) {
      case POPULATION:
        comparator = Comparator.comparingLong(CountryData::getValue);
        LOG.debug("Comparator for the field 'population' created");
        break;
      case COUNTRY_CODE:
        comparator = Comparator.comparing(countryData -> countryData.getCountry().getId());
        LOG.debug("Comparator for the field 'country_code' created");
        break;
      case COUNTRY_NAME:
        comparator = Comparator.comparing(countryData -> countryData.getCountry().getValue());
        LOG.debug("Comparator for the field 'country_name' created");
        break;
      default:
        throw new FailedToCreateComparatorException("unknown field " + criterion.getField());
    }
    if (SortingOrder.DESC == criterion.getOrder()) {
      LOG.debug("SortingOrder is DESC, Comparator reversed");
      return comparator.reversed();
    } else {
      return comparator;
    }
  }

  private static class FailedToCreateComparatorException extends RuntimeException {
    FailedToCreateComparatorException(String message) {
      super(message);
    }
  }
}
