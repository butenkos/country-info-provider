package com.butenkos.country.info.provider.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class CountryData {
  private Indicator indicator;
  private Country country;
  @JsonProperty("countryiso3code")
  private String countryIso3Code;
  private int date;
  private long value;
  private String unit;
  @JsonProperty("obs_status")
  private String obsStatus;
  private int decimal;

  public Indicator getIndicator() {
    return indicator;
  }

  public void setIndicator(Indicator indicator) {
    this.indicator = indicator;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public String getCountryIso3Code() {
    return countryIso3Code;
  }

  public void setCountryIso3Code(String countryIso3Code) {
    this.countryIso3Code = countryIso3Code;
  }

  public int getDate() {
    return date;
  }

  public void setDate(int date) {
    this.date = date;
  }

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getObsStatus() {
    return obsStatus;
  }

  public void setObsStatus(String obsStatus) {
    this.obsStatus = obsStatus;
  }

  public int getDecimal() {
    return decimal;
  }

  public void setDecimal(int decimal) {
    this.decimal = decimal;
  }

  @Override
  public String toString() {
    return "CountryData{" +
        "indicator=" + indicator +
        ", country=" + country +
        ", countryIso3Code='" + countryIso3Code + '\'' +
        ", date=" + date +
        ", value=" + value +
        ", unit='" + unit + '\'' +
        ", obsStatus='" + obsStatus + '\'' +
        ", decimal=" + decimal +
        '}';
  }
}




