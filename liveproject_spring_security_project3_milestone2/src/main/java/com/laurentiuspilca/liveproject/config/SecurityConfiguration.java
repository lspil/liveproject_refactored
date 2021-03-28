package com.laurentiuspilca.liveproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class SecurityConfiguration  {

  @Value("${publicKey}")
  private String publicKey;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(
          ServerHttpSecurity httpSecurity
  ) {
    return httpSecurity.authorizeExchange()
              .anyExchange().authenticated()
            .and()
              .oauth2ResourceServer()
                .jwt(c -> c.publicKey(publicKey()))
            .and().build();
  }

  private RSAPublicKey publicKey() {
    try {
      KeyFactory kFactory = KeyFactory.getInstance("RSA");
      X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes()));
      return  (RSAPublicKey) kFactory.generatePublic(keySpecX509);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException("Problem loading the public key for token validation.");
    }
  }
}
