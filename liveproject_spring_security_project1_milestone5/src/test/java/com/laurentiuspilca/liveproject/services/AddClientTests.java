package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.Client;
import com.laurentiuspilca.liveproject.entities.ClientGrantType;
import com.laurentiuspilca.liveproject.exceptions.ClientAlreadyExistsException;
import com.laurentiuspilca.liveproject.repositories.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AddClientTests {

  @Autowired
  private ClientService clientService;

  @MockBean
  private ClientRepository clientRepository;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Considering the client doesn't exist in the database " +
          "assert that the service adds the client record.")
  public void addClientWhenClientDoesntExistTest() {
    Client c = new Client();
    c.setClientId("client");
    c.setSecret("secret");
    c.setGrantTypes(List.of(new ClientGrantType()));

    when(clientRepository.findClientByClientId(c.getClientId()))
            .thenReturn(Optional.empty());

    clientService.addClient(c);

    verify(passwordEncoder).encode("secret");
    verify(clientRepository).save(c);
  }

  @Test
  @DisplayName("Considering the client already exists in the database " +
          "assert that the service throws an exception and " +
          "doesn't add the client record.")
  public void addClientWhenClientAlreadyExistsTest() {
    Client c = new Client();
    c.setClientId("client");
    c.setSecret("secret");

    when(clientRepository.findClientByClientId(c.getClientId()))
            .thenReturn(Optional.of(c));

    assertThrows(ClientAlreadyExistsException.class
            , () -> clientService.addClient(c));

    verify(passwordEncoder, never()).encode(c.getSecret());
    verify(clientRepository, never()).save(c);
  }
}
