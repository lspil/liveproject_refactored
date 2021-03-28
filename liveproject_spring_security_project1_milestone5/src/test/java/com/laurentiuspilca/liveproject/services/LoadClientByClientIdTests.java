package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.Client;
import com.laurentiuspilca.liveproject.repositories.ClientRepository;
import com.laurentiuspilca.liveproject.security.services.JpaClientDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoadClientByClientIdTests {

  @Autowired
  private JpaClientDetailsService clientDetailsService;

  @MockBean
  private ClientRepository clientRepository;

  @Test
  @DisplayName("Considering that the client doesn't exist in the database," +
          "test that the method throws ClientRegistrationException.")
  public void loadClientByClientIdClientDoesntExistTest() {
    String clientId = "client";

    when(clientRepository.findClientByClientId(clientId))
            .thenReturn(Optional.empty());

    assertThrows(ClientRegistrationException.class,
            () -> clientDetailsService.loadClientByClientId(clientId));
  }

  @Test
  @DisplayName("Considering that the client exists in the database," +
          "test that the method returns a valid ClientDetails instance.")
  public void loadClientByClientIdTest() {
    Client c = new Client();
    c.setClientId("client");

    when(clientRepository.findClientByClientId(c.getClientId()))
            .thenReturn(Optional.of(c));

    ClientDetails result = clientDetailsService.loadClientByClientId(c.getClientId());

    assertEquals(result.getClientId(), c.getClientId());
  }
}
