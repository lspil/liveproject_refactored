package com.laurentiuspilca.liveproject.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String username;
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private List<Authority> authorities;

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<Authority> authorities) {
    this.authorities = authorities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id == user.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", authorities=" + authorities +
            '}';
  }
}
