package com.laurentiuspilca.liveproject.services.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestSecurityContextFactory implements WithSecurityContextFactory<TestUser> {

  @Override
  public SecurityContext createSecurityContext(TestUser testUser) {
    Map<String, Object> headers = new HashMap<>();
    headers.put("user_name", testUser.username());

    Map<String, Object> claims = new HashMap<>();
    claims.put("user_name", testUser.username());

    Instant issuesAt = Instant.now();
    Instant expiresAt = issuesAt.plusSeconds(1000);

    List<GrantedAuthority> authorities = Arrays.asList(testUser.authorities())
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    Jwt jwt = new Jwt("token", issuesAt, expiresAt, headers, claims);
    Authentication a = new JwtAuthenticationToken(jwt, authorities);

    a.setAuthenticated(true);

    var context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(a);

    return context;
  }
}
