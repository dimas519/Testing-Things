package com.dimas519.apigateway;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ApiGW {
    private WebClient webClient;
    private final WebClient.Builder builder;

    @Value("${stx.api1.url}")
    private String api1Url;

    @Value("${stx.api1.port}")
    private int api1Port;

    @Value("${stx.api1.ssl}")
    private boolean api1Ssl;


    public ApiGW(WebClient.Builder builder) {
        this.builder = builder;
    }

    @PostConstruct
    public void init() {
        this.webClient = builder.baseUrl((api1Ssl ? "https://" : "http://") + api1Url + ":" + api1Port).build();
    }





    @GetMapping(value = "/",produces = "text/json")
    public String root(){
        return combine();
    }


    @GetMapping(value = "/combine",produces = "text/json")
    public String combine(){
        return webClient.get()
                .uri("/userCombine")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


}
