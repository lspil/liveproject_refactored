package com.laurentiuspilca.liveproject.controllers;

import com.laurentiuspilca.liveproject.entities.Client;
import com.laurentiuspilca.liveproject.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping
  public void addClient(@RequestBody Client client) {
    clientService.addClient(client);
  }

  @GetMapping
  public List<Client> getAllClients() {
    return clientService.getAllClients();
  }
}
