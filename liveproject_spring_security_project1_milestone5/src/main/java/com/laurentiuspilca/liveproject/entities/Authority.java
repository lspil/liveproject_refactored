package com.laurentiuspilca.liveproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;

  @ManyToOne
  @JsonIgnore
  private User user;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Authority authority = (Authority) o;
    return id == authority.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Authority{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", user=" + user +
            '}';
  }
}
