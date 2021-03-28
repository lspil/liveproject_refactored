package com.laurentiuspilca.liveproject.model;

import java.util.List;
import java.util.Objects;

public class HealthProfile {

  private int id;

  private String username;

  private List<HealthMetric> metrics;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<HealthMetric> getMetrics() {
    return metrics;
  }

  public void setMetrics(List<HealthMetric> metrics) {
    this.metrics = metrics;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HealthProfile that = (HealthProfile) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
