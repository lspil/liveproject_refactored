package com.laurentiuspilca.liveproject.token;

import com.laurentiuspilca.liveproject.entities.Authority;
import com.laurentiuspilca.liveproject.entities.Client;
import com.laurentiuspilca.liveproject.entities.ClientGrantType;
import com.laurentiuspilca.liveproject.entities.User;
import com.laurentiuspilca.liveproject.repositories.ClientRepository;
import com.laurentiuspilca.liveproject.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GenerateTokenTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private ClientRepository clientRepository;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Considering the client and the user exist in the database and the request " +
          "is a valid one, assert that the HTTP status is 200 OK and the authorization server " +
          "generates the access token.")
  void generateTokenValidUserAndClientTest() throws Exception {
    ClientGrantType gt = new ClientGrantType();
    gt.setGrantType("password");

    Client client = new Client();
    client.setClientId("client");
    client.setSecret("secret");
    client.setGrantTypes(List.of(gt));
    client.setScope("read");

    Authority a = new Authority();
    a.setName("read");

    User user = new User();
    user.setUsername("user");
    user.setPassword("password");
    user.setAuthorities(List.of(a));

    when(userRepository.findUserByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

    when(clientRepository.findClientByClientId(client.getClientId()))
            .thenReturn(Optional.of(client));

    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

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
    Authority a = new Authority();
    a.setName("read");

    User user = new User();
    user.setUsername("user");
    user.setPassword("password");
    user.setAuthorities(List.of(a));

    when(userRepository.findUserByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

    when(clientRepository.findClientByClientId("client"))
            .thenReturn(Optional.empty());

    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

    mvc.perform(
            post("/oauth/token")
                    .with(httpBasic("client", "secret"))
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
    ClientGrantType gt = new ClientGrantType();
    gt.setGrantType("password");

    Client client = new Client();
    client.setClientId("client");
    client.setSecret("secret");
    client.setGrantTypes(List.of(gt));
    client.setScope("read");

    when(userRepository.findUserByUsername("user"))
            .thenReturn(Optional.empty());

    when(clientRepository.findClientByClientId(client.getClientId()))
            .thenReturn(Optional.of(client));

    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

    mvc.perform(
            post("/oauth/token")
                    .with(httpBasic("client", "secret"))
                    .queryParam("grant_type", "password")
                    .queryParam("username", "user")
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
    ClientGrantType gt = new ClientGrantType();
    gt.setGrantType("password");

    Client client = new Client();
    client.setClientId("client");
    client.setSecret("secret");
    client.setGrantTypes(List.of(gt));
    client.setScope("read");

    Authority a = new Authority();
    a.setName("read");

    User user = new User();
    user.setUsername("user");
    user.setPassword("password");
    user.setAuthorities(List.of(a));

    when(userRepository.findUserByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

    when(clientRepository.findClientByClientId(client.getClientId()))
            .thenReturn(Optional.of(client));

    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

    mvc.perform(
            post("/oauth/token")
                    .with(httpBasic("client", "secret"))
                    .queryParam("grant_type", "password")
                    .queryParam("username", "user")
                    .queryParam("password", "password")
                    .queryParam("scope", "read")
    )
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.access_token").doesNotExist());

  }

  @Test
  @DisplayName("Considering the request is valid and the client has the refresh_token grant type " +
          "assert that the authorization server issues both the access token and the refresh token.")
  void generateRefreshTokenTest() throws Exception {
    ClientGrantType gt1 = new ClientGrantType();
    gt1.setGrantType("password");

    ClientGrantType gt2 = new ClientGrantType();
    gt2.setGrantType("refresh_token");

    Client client = new Client();
    client.setClientId("client");
    client.setSecret("secret");
    client.setGrantTypes(List.of(gt1, gt2));
    client.setScope("read");

    Authority a = new Authority();
    a.setName("read");

    User user = new User();
    user.setUsername("user");
    user.setPassword("password");
    user.setAuthorities(List.of(a));

    when(userRepository.findUserByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

    when(clientRepository.findClientByClientId(client.getClientId()))
            .thenReturn(Optional.of(client));

    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

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
