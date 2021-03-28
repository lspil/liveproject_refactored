package com.laurentiuspilca.liveproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "client_grant_types")
public class ClientGrantType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "grant_type")
  private String grantType;

  @ManyToOne
  @JsonIgnore
  private Client client;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getGrantType() {
    return grantType;
  }

  public void setGrantType(String grantType) {
    this.grantType = grantType;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ClientGrantType that = (ClientGrantType) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "ClientGrantType{" +
            "id=" + id +
            ", grantType='" + grantType + '\'' +
            ", client=" + client +
            '}';
  }
}
