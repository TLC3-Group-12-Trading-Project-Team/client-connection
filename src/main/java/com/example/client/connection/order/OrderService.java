package com.example.client.connection.order;

import com.example.client.connection.Client.Client;
import com.example.client.connection.Client.ClientRepository;
import com.example.client.connection.Client.ClientService;
import com.example.client.connection.portfolio.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, PortfolioRepository portfolioRepository, ClientRepository clientRepository ){
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public void createOrders(Orders orders){
        this.orderRepository.save(orders);
    }

    public void updateClientOrderBalance(Long orderId, double amount) {
        Optional<Orders> order = this.orderRepository.findById(orderId);
        Long portfolioId = order.get().getPortfolio().getId();
        Long clientID = this.portfolioRepository.findById(portfolioId).get().getClient().getId();
        Client client = this.clientRepository.findById(clientID).get();
        client.setBalance(amount);
        this.clientRepository.save(client);
    }
}
