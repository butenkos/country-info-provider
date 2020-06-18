package com.butenkos.country.info.provider.service;

import com.butenkos.country.info.provider.dao.CountryDataDao;
import com.butenkos.country.info.provider.dao.entity.CountryData;
import com.butenkos.country.info.provider.model.request.SortingCriterion;
import com.butenkos.country.info.provider.model.request.SortingOrder;
import com.butenkos.country.info.provider.model.response.CountryDataResponse;
import com.butenkos.country.info.provider.model.response.CountryDataViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CountryDataExplorerImpl implements CountryDataExplorer {
  private final CountryDataDao dao;

  @Autowired
  public CountryDataExplorerImpl(CountryDataDao dao) {
    this.dao = dao;
  }

  @Override
  public CountryDataResponse foo(List<SortingCriterion> sortingCriteria, int limit) {
    System.out.println("BBBBBBBBBB: " + sortingCriteria);
    Stream<CountryData> stream = dao.getData().stream();

    final Optional<Comparator<CountryData>> comparator = createComparator(sortingCriteria);
    if (comparator.isPresent()) {
      stream = stream.sorted(comparator.get());
    }
    if (limit > 0) {
      stream = stream.limit(limit);
    }
    return new CountryDataResponse(stream.map(CountryDataViewModel::new).collect(Collectors.toList()));
  }

  //TODO
  private Optional<Comparator<CountryData>> createComparator(List<SortingCriterion> sortingCriteria) {
    return sortingCriteria.stream()
        .map(this::foo)
        .reduce(Comparator::thenComparing);
  }

  Comparator<CountryData> foo(SortingCriterion criterion) {
    Comparator<CountryData> comparator;
    switch (criterion.getField()) {
      case POPULATION:
        comparator = Comparator.comparingLong(CountryData::getValue);
        break;
      case COUNTRY_CODE:
        comparator = Comparator.comparing(countryData -> countryData.getCountry().getId());
        break;
      case COUNTRY_NAME:
        comparator = Comparator.comparing(countryData -> countryData.getCountry().getValue());
        break;
      default:
        throw new RuntimeException();
    }
    if (SortingOrder.DESC == criterion.getOrder()) {
      return comparator.reversed();
    } else {
      return comparator;
    }
  }

  public static void main(String[] args) {
//    CountryData d1 = new CountryData();
//    d1.setValue(3);
//    CountryData d2 = new CountryData();
//    d2.setValue(33);
//    CountryData d3 = new CountryData();
//    d3.setValue(2);
//    CountryData d4 = new CountryData();
//    d4.setValue(22);
//    CountryData d5 = new CountryData();
//    d5.setValue(7);
//    CountryData d6 = new CountryData();
//    d6.setValue(27);
//    CountryData d7 = new CountryData();
//    d7.setValue(355);
//
//    final List<CountryData> collect = Stream.of(d1, d2, d3, d4, d5, d6, d7)
//        .sorted(createComparator(new SortingCriterion(SortingField.POPULATION, SortingOrder.ASC)))
//        .collect(Collectors.toList());
//    System.out.println(collect);

  }
}
