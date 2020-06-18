package com.butenkos.country.info.provider.dao.entity;

@SuppressWarnings("unused")
public class Country {
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
    return "Country{" +
        "id='" + id + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
