package com.butenkos.country.info.provider.endpoint;

import com.butenkos.country.info.provider.model.request.FilteringCriteria;
import com.butenkos.country.info.provider.model.request.SortingCriterion;
import com.butenkos.country.info.provider.model.response.ErrorResponse;
import com.butenkos.country.info.provider.service.CountryDataExplorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryDataController {
  private static final Logger LOG = LoggerFactory.getLogger(CountryDataController.class);
  private final CountryDataExplorer dataExplorer;

  @Autowired
  public CountryDataController(CountryDataExplorer dataExplorer) {
    this.dataExplorer = dataExplorer;
  }

  @GetMapping("/country")
  public ResponseEntity<?> getCountryData(
      @RequestParam(required = false) List<SortingCriterion> sort,
      @RequestParam(required = false) Integer limit
  ) {
    try {
      return ResponseEntity.ok(dataExplorer.getCountryData(sort, new FilteringCriteria(limit)));
    } catch (Exception e) {
      LOG.error("error, sort={}, limit={}", sort, limit, e);
      return ResponseEntity
          .badRequest()
          .body(new ErrorResponse("500", "Internal Server Error", e.getMessage(), "/country"));
    }
  }
}
