package com.laurentiuspilca.liveproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class TokenStoreConfiguration {

  @Value("${password}")
  private String password;

  @Value("${privateKey}")
  private String privateKey;

  @Value("${alias}")
  private String alias;

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(converter());
  }

  @Bean
  public JwtAccessTokenConverter converter() {
    var converter = new JwtAccessTokenConverter();

    KeyStoreKeyFactory keyStoreKeyFactory =
            new KeyStoreKeyFactory(
                    new ClassPathResource(privateKey),
                    password.toCharArray());
    converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));

    return converter;
  }
}
