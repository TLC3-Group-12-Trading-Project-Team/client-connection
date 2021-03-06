package com.example.client.connection.portfolio;

import com.example.client.connection.Client.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService){
        this.portfolioService = portfolioService;
    }

    @GetMapping(path = "{clientId}")
    public List<Portfolio> getPortfolio(@PathVariable("clientId") Long clientId){
        return this.portfolioService.getAllPortfolio(clientId);
    }

    @PostMapping(path = "/create")
    public ResponseData registerNewPortfolio(@RequestBody Portfolio portfolio){
        return this.portfolioService.addPortfolio(portfolio);
    }

    @DeleteMapping(path = "{id}")
    public ResponseData remove(@PathVariable("id") Long id){
        return this.portfolioService.removePortfolio(id);
    }

//    @PutMapping(path = "{id}")
//    public Response update(@PathVariable("id") Long id){
//        return this.portfolioService.updatePortfolio(id);
//    }

    @GetMapping(path = "single/{id}")
    public Portfolio single(@PathVariable("id") Long id){

        return this.portfolioService.getSinglePortfolio(id);
    }

}

