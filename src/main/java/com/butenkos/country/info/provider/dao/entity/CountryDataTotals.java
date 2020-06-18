package com.butenkos.country.info.provider.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@SuppressWarnings("unused")
public class CountryDataTotals implements Serializable {
  private int page;
  private int pages;
  @JsonProperty("per_page")
  private int perPage;
  private int total;
  @JsonProperty("sourceid")
  private String sourceId;
  @JsonProperty("lastupdated")
  private String lastUpdated;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public int getPerPage() {
    return perPage;
  }

  public void setPerPage(int perPage) {
    this.perPage = perPage;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getSourceId() {
    return sourceId;
  }

  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(String lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  @Override
  public String toString() {
    return "CountryDataTotals{" +
        "page=" + page +
        ", pages=" + pages +
        ", perPage=" + perPage +
        ", total=" + total +
        ", sourceId='" + sourceId + '\'' +
        ", lastUpdated=" + lastUpdated +
        '}';
  }
}
