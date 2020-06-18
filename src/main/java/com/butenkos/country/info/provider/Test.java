package com.butenkos.country.info.provider;

import com.butenkos.country.info.provider.model.request.SortingCriterion;
import com.butenkos.country.info.provider.model.response.CountryDataResponse;
import com.butenkos.country.info.provider.service.CountryDataExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class Test {

  @Autowired
  CountryDataExplorer foo;


  @GetMapping("/foo")
  public CountryDataResponse foo(
      @RequestParam(required = false) List<SortingCriterion> sort,
      @RequestParam(required = false) Integer limit
  ) {
    return foo.foo(
        sort != null ? sort : Collections.emptyList(),
        limit != null ? limit : 3
    );
  }
}
