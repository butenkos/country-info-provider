//package com.butenkos.country.info.provider.dao;
//
//import com.butenkos.country.info.provider.dao.entity.CountryData;
//import com.butenkos.country.info.provider.dao.entity.CountryDataTotals;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.List;
//
//@Component
//public class Foo {
//
//  public List<CountryData> foo() {
//    WebClient client = WebClient.create(
//        "http://api.worldbank.org/v2/country/all/indicator/SP.POP.TOTL?date=2018&format=json"
//    );
//    final WebClient.RequestHeadersUriSpec<?> uriSpec = client.get();
//    final List<Object> block = uriSpec.retrieve().bodyToMono(new ParameterizedTypeReference<List<Object>>() {
//    }).block();
//    System.out.println(block);
//    ObjectMapper objectMapper = new ObjectMapper();
//    System.out.println(objectMapper.convertValue(block.get(0), CountryDataTotals.class));
//
//    return objectMapper.convertValue(block.get(1), new TypeReference<List<CountryData>>() {
//    });
//  }
//}
