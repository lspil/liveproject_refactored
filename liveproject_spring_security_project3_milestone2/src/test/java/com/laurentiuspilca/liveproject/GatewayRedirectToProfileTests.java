package com.laurentiuspilca.liveproject;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.laurentiuspilca.liveproject.model.HealthProfile;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@SpringBootTest
@AutoConfigureWebTestClient
public class GatewayRedirectToProfileTests {

  @Autowired
  private WebTestClient client;

  private static WireMockServer wireMockServer;

  @BeforeAll
  static void init() {
    wireMockServer = new WireMockServer(new WireMockConfiguration().port(7070));
    wireMockServer.start();
    WireMock.configureFor("localhost", 7070);
  }

  @Test
  public void redirectToProfileGetTest() {
    HealthProfile p = new HealthProfile();
    p.setUsername("john");

    stubFor(WireMock.get(urlMatching("/profile/bill"))
            .willReturn(aResponse()
                    .withStatus(OK.value())));

    client.mutateWith(mockJwt())
            .get()
            .uri("/profile/bill")
            .exchange()
            .expectStatus().isOk();

  }

  @Test
  public void redirectToProfileGetNoAuthTest() {
    HealthProfile p = new HealthProfile();
    p.setUsername("john");

    client.get()
            .uri("/profile/bill")
            .exchange()
            .expectStatus().isUnauthorized();

  }

  @Test
  public void redirectToProfilePostTest() {
    HealthProfile p = new HealthProfile();
    p.setUsername("john");

    stubFor(WireMock.post(urlMatching("/profile"))
            .willReturn(aResponse()
                    .withStatus(OK.value())));

    client.mutateWith(mockJwt())
            .mutateWith(csrf())
            .post()
            .uri("/profile")
            .bodyValue(p)
            .exchange()
            .expectStatus().isOk();

  }

  @Test
  public void redirectToProfilePostNoAuthTest() {
    HealthProfile p = new HealthProfile();
    p.setUsername("john");

    client.mutateWith(csrf())
            .post()
            .uri("/profile")
            .bodyValue(p)
            .exchange()
            .expectStatus().isUnauthorized();

  }

  @Test
  public void redirectToProfileDeleteTest() {
    stubFor(WireMock.delete(urlMatching("/profile/bill"))
            .willReturn(aResponse()
                    .withStatus(OK.value())));

    client.mutateWith(mockJwt())
            .mutateWith(csrf())
            .delete()
            .uri("/profile/bill")
            .exchange()
            .expectStatus().isOk();

  }

  @Test
  public void redirectToProfileDeleteNoAuthTest() {
    client.mutateWith(csrf())
            .delete()
            .uri("/profile/bill")
            .exchange()
            .expectStatus().isUnauthorized();

  }

  @AfterAll
  static void tearDown() {
    wireMockServer.stop();
  }

}
