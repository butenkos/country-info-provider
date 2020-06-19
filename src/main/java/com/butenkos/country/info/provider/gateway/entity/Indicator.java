package com.butenkos.country.info.provider.gateway.entity;

import java.util.Objects;

@SuppressWarnings("unused")
public class Indicator {
  private String id;
  private String value;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Indicator{" +
        "id='" + id + '\'' +
        ", value='" + value + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Indicator indicator = (Indicator) o;
    return Objects.equals(id, indicator.id) &&
        Objects.equals(value, indicator.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value);
  }
}
