package com.laurentiuspilca.liveproject.model;

import com.laurentiuspilca.liveproject.model.enums.HealthMetricType;

public class UserHealthData {

  private String username;
  private HealthMetricType healthMetricType;
  private double value;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public HealthMetricType getHealthMetricType() {
    return healthMetricType;
  }

  public void setHealthMetricType(HealthMetricType healthMetricType) {
    this.healthMetricType = healthMetricType;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }
}
