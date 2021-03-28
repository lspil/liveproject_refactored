package com.laurentiuspilca.liveproject.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "client_id")
  private String clientId;

  private String secret;

  private String scope;

  @Column(name = "rediect_uri")
  private String redirectURI;

  @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private List<ClientGrantType> grantTypes;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getRedirectURI() {
    return redirectURI;
  }

  public void setRedirectURI(String redirectURI) {
    this.redirectURI = redirectURI;
  }

  public List<ClientGrantType> getGrantTypes() {
    return grantTypes;
  }

  public void setGrantTypes(List<ClientGrantType> grantTypes) {
    this.grantTypes = grantTypes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Client client = (Client) o;
    return id == client.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }


}
