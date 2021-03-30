package com.example.client.connection.SoapClient;


import com.example.client.connection.ClientOrders.OrderRequest;
import com.example.client.connection.ClientOrders.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class SoapClient {

    @Autowired
    private Jaxb2Marshaller marshaller;


    private WebServiceTemplate template;

    public OrderResponse getOrderStatus(OrderRequest request) {
        template = new WebServiceTemplate(marshaller);
        OrderResponse response = (OrderResponse) template.marshalSendAndReceive("http://18.193.123.168:47000/ws",
                request);
        return response ;
    }

}