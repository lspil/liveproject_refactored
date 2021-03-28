package com.laurentiuspilca.liveproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "health_profile")
public class HealthProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String username;

  @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE)
  @JsonIgnore
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

  @Override
  public String toString() {
    return "HealthProfile{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", metrics=" + metrics +
            '}';
  }
}
