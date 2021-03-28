package com.laurentiuspilca.liveproject.security.model;

import com.laurentiuspilca.liveproject.entities.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDetailsSecurityWrapper implements ClientDetails {

  private final Client client;

  public ClientDetailsSecurityWrapper(Client client) {
    this.client = client;
  }

  @Override
  public String getClientId() {
    return client.getClientId();
  }

  @Override
  public String getClientSecret() {
    return client.getSecret();
  }

  @Override
  public boolean isSecretRequired() {
    return true;
  }

  @Override
  public boolean isScoped() {
    return true;
  }

  @Override
  public Set<String> getScope() {
    return Set.of(client.getScope());
  }

  @Override
  public Set<String> getAuthorizedGrantTypes() {
    return client.getGrantTypes().stream()
            .map(gt -> gt.getGrantType())
            .collect(Collectors.toSet());
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return Set.of(client.getRedirectURI());
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return Set.of(new SimpleGrantedAuthority(client.getScope()));
  }

  @Override
  public Set<String> getResourceIds() {
    return null;
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return null;
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return null;
  }

  @Override
  public boolean isAutoApprove(String s) {
    return false;
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    return null;
  }
}
