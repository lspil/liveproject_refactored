package com.laurentiuspilca.liveproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

  @Value("${business.logic.server.url}")
  private String businessLogicServerURL;

  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
            .route(p -> p
                    .path("/metric/**")
                    .uri(businessLogicServerURL + "/metric"))
            .route(p -> p
                    .path("/profile/**")
                    .uri(businessLogicServerURL + "/profile"))
            .route(p -> p
                    .path("/advice/**")
                    .uri(businessLogicServerURL + "/advice"))
            .build();
  }
}
