package com.dimas519.api1;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class TodosPost {
    private WebClient.Builder webClient;

    private final  String api1="https://jsonplaceholder.typicode.com/todos";
    private String api2;

    @Value("${stx.api2.url}")
    private String api2Url;

    @Value("${stx.api2.port}")
    private int api2Port;

    @Value("${stx.api2.ssl}")
    private boolean api2Ssl;


    public TodosPost(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder;
    }


    @PostConstruct
    public void init() {
        this.api2 = (api2Ssl ? "https://" : "http://") + api2Url + ":" + api2Port + "/posts";
    }


    public String getTodos() {
        WebClient client = this.webClient
                .baseUrl(api1)
                .build();

        return client.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    public String getPosts(){
        System.out.println(api2);
        WebClient client = this.webClient
                .baseUrl(this.api2)
                .build();

        return client.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }




}
