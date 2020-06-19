package com.butenkos.country.info.provider.gateway.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CountryData that = (CountryData) o;
    return date == that.date &&
        value == that.value &&
        decimal == that.decimal &&
        Objects.equals(indicator, that.indicator) &&
        Objects.equals(country, that.country) &&
        Objects.equals(countryIso3Code, that.countryIso3Code) &&
        Objects.equals(unit, that.unit) &&
        Objects.equals(obsStatus, that.obsStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indicator, country, countryIso3Code, date, value, unit, obsStatus, decimal);
  }
}




