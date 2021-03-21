package com.example.client.connection.order;

import com.example.client.connection.Client.ResponseData;
import com.example.client.connection.ClientOrders.OrderRequest;
import com.example.client.connection.SoapClient.SoapClient;
import com.example.client.connection.portfolio.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path="order")
public class OrderController {

    @Autowired
    private SoapClient client;

    private final OrderService orderService;
    private  final PortfolioService portfolioService;

    @Autowired
    public OrderController(PortfolioService portfolioService, OrderService orderService){
        this.portfolioService = portfolioService;
        this.orderService = orderService;
    }

    @PostMapping("/getOrderStatus")
    public ResponseData createValidatedOrders(@RequestBody OrderRequest request) {
        ResponseData response = new ResponseData();
        if(client.getOrderStatus(request).isIsOrderValid()){
            if(portfolioService.getPortfolio((long) request.getPortfolioId())!=null){
                Orders orders = new Orders();
                orders.setStatus("OPEN");
                orders.setSide(request.getSide());
                orders.setProduct(request.getProduct());
                orders.setCreatedAt(LocalDateTime.now());
                orders.setPrice(request.getPrice());
                orders.setQuantity(request.getQuantity());
                orders.setPortfolio(portfolioService.getPortfolio((long) request.getPortfolioId()));
                orderService.createOrders(orders);
                response.setCode(HttpStatus.CREATED.value());
                response.setStatus("Created Successfully");
                response.setData(client.getOrderStatus(request));
            }else{
                response.setCode(HttpStatus.BAD_REQUEST.value());
                response.setStatus("Portflio does not exist");
            }
        }else{
            response.setName("Order is invalid");
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setData(client.getOrderStatus(request));
        }

        return response;
    }

}