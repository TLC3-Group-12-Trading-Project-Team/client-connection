package com.example.client.connection.order;

import com.example.client.connection.Client.ResponseData;
import com.example.client.connection.ClientOrders.OrderRequest;
import com.example.client.connection.SoapClient.SoapClient;
import com.example.client.connection.portfolio.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
@CrossOrigin
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
                response.setCode(HttpStatus.CREATED.value());
                response.setStatus("Created Successfully");
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