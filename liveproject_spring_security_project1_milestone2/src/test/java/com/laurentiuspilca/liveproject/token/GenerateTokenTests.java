package com.laurentiuspilca.liveproject.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GenerateTokenTests {

  @Autowired
  private MockMvc mvc;

  @Test
  @DisplayName("Considering the client and the user exist in the database and the request " +
          "is a valid one, assert that the HTTP status is 200 OK and the authorization server " +
          "generates the access token.")
  void generateTokenValidUserAndClientTest() throws Exception {

    mvc.perform(
            post("/oauth/token")
                    .with(httpBasic("client", "secret"))
                    .queryParam("grant_type", "password")
                    .queryParam("username", "user")
                    .queryParam("password", "password")
                    .queryParam("scope", "read")
    )
            .andExpect(jsonPath("$.access_token").exists())
            .andExpect(status().isOk());

  }

  @Test
  @DisplayName("Considering the client authenticating the request does not exist " +
          "assert that the response status is HTTP 4XX (client error) and " +
          "the authorization server doesn't generate the access token.")
  void generateTokenInvalidClientTest() throws Exception {

    mvc.perform(
            post("/oauth/token")
                    .with(httpBasic("other_client", "secret"))
                    .queryParam("grant_type", "password")
                    .queryParam("username", "user")
                    .queryParam("password", "password")
                    .queryParam("scope", "read")
    )
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.access_token").doesNotExist());

  }

  @Test
  @DisplayName("Considering the user authenticating the request does not exist " +
          "assert that the response status is HTTP 4XX (client error) and " +
          "the authorization server doesn't generate the access token.")
  void generateTokenInvalidUserTest() throws Exception {

    mvc.perform(
            post("/oauth/token")
                    .with(httpBasic("client", "secret"))
                    .queryParam("grant_type", "password")
                    .queryParam("username", "other_user")
                    .queryParam("password", "password")
                    .queryParam("scope", "read")
    )
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.access_token").doesNotExist());

  }

  @Test
  @DisplayName("Considering the password for either client or user is not valid " +
          "assert that the response status is HTTP 4XX (client error) and " +
          "the authorization server doesn't generate the access token.")
  void generateTokenPasswordNotValidTest() throws Exception {

    mvc.perform(
            post("/oauth/token")
                    .with(httpBasic("client", "other_secret"))
                    .queryParam("grant_type", "password")
                    .queryParam("username", "user")
                    .queryParam("password", "other_password")
                    .queryParam("scope", "read")
    )
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.access_token").doesNotExist());

  }

  @Test
  @DisplayName("Considering the request is valid and the client has the refresh_token grant type " +
          "assert that the authorization server issues both the access token and the refresh token.")
  void generateRefreshTokenTest() throws Exception {

    mvc.perform(
            post("/oauth/token")
                    .with(httpBasic("client", "secret"))
                    .queryParam("grant_type", "password")
                    .queryParam("username", "user")
                    .queryParam("password", "password")
                    .queryParam("scope", "read")
    )
            .andExpect(jsonPath("$.access_token").exists())
            .andExpect(jsonPath("$.refresh_token").exists())
            .andExpect(status().isOk());

  }
}
