package com.laurentiuspilca.liveproject.model;

import com.laurentiuspilca.liveproject.model.enums.HealthMetricType;

import java.util.Objects;

public class HealthMetric {


  private int id;

  private double value;

  private HealthMetricType type;

  private HealthProfile profile;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public HealthMetricType getType() {
    return type;
  }

  public void setType(HealthMetricType type) {
    this.type = type;
  }

  public HealthProfile getProfile() {
    return profile;
  }

  public void setProfile(HealthProfile profile) {
    this.profile = profile;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HealthMetric that = (HealthMetric) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
