package com.laurentiuspilca.liveproject.proxy;

import com.laurentiuspilca.liveproject.managers.TokenManager;
import com.laurentiuspilca.liveproject.model.HealthAdvice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class HealthSystemProxy {

  public static final String AUTHORIZATION = "Authorization";
  @Value("${health.system.base.url}")
  private String healthSystemBaseUrl;

  private final TokenManager tokenManager;

  private final RestTemplate restTemplate;

  public HealthSystemProxy(TokenManager tokenManager, RestTemplate restTemplate) {
    this.tokenManager = tokenManager;
    this.restTemplate = restTemplate;
  }

  public void sendAdvice(List<HealthAdvice> healthAdvices) {
    String token = tokenManager.getAccessToken();

    String url = healthSystemBaseUrl + "/advice";

    HttpHeaders headers = new HttpHeaders();
    headers.add(AUTHORIZATION, "Bearer " + token);
    HttpEntity<List<HealthAdvice>> request =
            new HttpEntity<>(healthAdvices, headers);

    restTemplate.postForObject(url, request, Void.class);
  }
}
