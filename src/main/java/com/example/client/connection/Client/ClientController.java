package com.example.client.connection.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClient(){
        return this.clientService.getClients();
    }

    @PostMapping(path="/signup")
    public ResponseData registerNewClient(@RequestBody Client client){
        return this.clientService.addNewClient(client);
    }

    @PostMapping(path="/login")
    public ResponseData loginClient(@RequestBody Client client){
        return this.clientService.loginClient(client);
    }

    @GetMapping(path = "{clientId}")
    public Client getClientById(@PathVariable("clientId") Long clientId){
        return this.clientService.getClient(clientId);
    }

    @GetMapping(path = "/balance/{clientId}")
    public Double getClientBalance(@PathVariable("clientId") Long clientId){
        return this.clientService.clientBalance(clientId);
    }

    @PutMapping(path = "/update-balance/{clientId}")
    public void updateClientBalance(@PathVariable("clientId") Long clientId, @RequestBody Double amount){
        this.clientService.updateClientBalance(clientId,amount);
    }
}
