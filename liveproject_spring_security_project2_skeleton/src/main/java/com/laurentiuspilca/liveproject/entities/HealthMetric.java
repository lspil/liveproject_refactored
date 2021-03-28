package com.laurentiuspilca.liveproject.entities;

import com.laurentiuspilca.liveproject.entities.enums.HealthMetricType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "health_metric")
public class HealthMetric {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Basic
  private double value;

  @Enumerated(EnumType.STRING)
  private HealthMetricType type;

  @ManyToOne
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

  @Override
  public String toString() {
    return "HealthMetric{" +
            "id=" + id +
            ", value=" + value +
            ", type=" + type +
            ", profile=" + profile.getUsername() +
            '}';
  }
}
