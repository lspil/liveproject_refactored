package com.laurentiuspilca.liveproject.config;

import com.laurentiuspilca.liveproject.security.services.JpaClientDetailsService;
import com.laurentiuspilca.liveproject.security.services.JpaUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration
        extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;

  private final JpaClientDetailsService clientDetailsService;

  private final TokenStore tokenStore;

  private final JwtAccessTokenConverter converter;

  private final UserDetailsService userDetailsService;

  public AuthorizationServerConfiguration(AuthenticationManager authenticationManager,
                                          JpaClientDetailsService clientDetailsService,
                                          JpaUserDetailsService userDetailsService,
                                          TokenStore tokenStore,
                                          JwtAccessTokenConverter converter) {
    this.authenticationManager = authenticationManager;
    this.clientDetailsService = clientDetailsService;
    this.userDetailsService = userDetailsService;
    this.tokenStore = tokenStore;
    this.converter = converter;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.authenticationManager(authenticationManager)
      .tokenStore(tokenStore)
      .accessTokenConverter(converter)
      .userDetailsService(userDetailsService);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }

}
