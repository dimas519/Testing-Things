package com.dimas519.api1;




import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.reactive.function.client.WebClient;



@RestController
public class API1Controller {

    private final TodosPost todosPost;

    private final RedisConnector redisConnector;



    public API1Controller(WebClient.Builder webClientBuilder,RedisConnector redisConnector, TodosPost todosPost) {
        this.todosPost=todosPost;
        this.redisConnector=redisConnector;


    }





    @GetMapping(value = "/userCombine",produces = "text/json")
    public String getUserCombine() throws JsonProcessingException {



        System.out.println("/userCombine");
        String todos=todosPost.getTodos();

        String posts=todosPost.getPosts();


        String getUser=redisConnector.getData("user:1");



        String result=CombineUser.getResult(getUser,todos,posts);









        return result;
    }


}
