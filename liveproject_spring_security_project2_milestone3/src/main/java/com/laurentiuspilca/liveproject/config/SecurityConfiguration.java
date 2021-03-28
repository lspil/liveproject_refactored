package com.laurentiuspilca.liveproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Value("${publicKey}")
  private String publicKey;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.oauth2ResourceServer(
            c -> c.jwt(
                    j -> {
                      j.decoder(jwtDecoder());
                      j.jwtAuthenticationConverter(jwtAuthenticationConverter());
                    }
            )
    );
    http.authorizeRequests()
            .mvcMatchers(HttpMethod.DELETE, "/profile/**").hasAuthority("admin")
            .mvcMatchers(HttpMethod.DELETE, "/metric/**").hasAuthority("admin")
            .mvcMatchers(HttpMethod.POST, "/advice/**").hasAuthority("advice")
            .anyRequest().authenticated();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    try {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      var key = Base64.getDecoder().decode(publicKey);

      var x509 = new X509EncodedKeySpec(key);
      var rsaKey = (RSAPublicKey) keyFactory.generatePublic(x509);
      return NimbusJwtDecoder.withPublicKey(rsaKey).build();
    } catch (Exception e) {
      throw new RuntimeException("Wrong public key");
    }
  }

  @Bean
  public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
    return new SecurityEvaluationContextExtension();
  }

  private Converter<Jwt,? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

    converter.setJwtGrantedAuthoritiesConverter(j -> {
      List<String> authorities = (List<String>) j.getClaims().get("authorities");

      return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    });


    return converter;
  }
}
