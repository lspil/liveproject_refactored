package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.Client;
import com.laurentiuspilca.liveproject.exceptions.ClientAlreadyExistsException;
import com.laurentiuspilca.liveproject.repositories.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

  private final ClientRepository clientRepository;

  private final PasswordEncoder passwordEncoder;

  public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
    this.clientRepository = clientRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void addClient(Client client) {
    Optional<Client> c = clientRepository.findClientByClientId(client.getClientId());

    if (c.isEmpty()) {
      client.setSecret(passwordEncoder.encode(client.getSecret()));
      client.getGrantTypes().forEach(gt -> gt.setClient(client));
      clientRepository.save(client);
    } else {
      throw new ClientAlreadyExistsException("Client already exists.");
    }
  }

  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }
}
