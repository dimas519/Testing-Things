package com.dimas519.api1;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.*;


//logical class
public class CombineUser {

    public static String getResult(String user, String todos,String post) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();

//        try {
            //user
            List<LinkedHashMap<String, Object>> userArray =
                    mapper.readValue(user, mapper.getTypeFactory().constructCollectionType(List.class, LinkedHashMap.class));

            //todos
            List<LinkedHashMap<String, Object>> todosArray =
                    mapper.readValue(todos, mapper.getTypeFactory().constructCollectionType(List.class, LinkedHashMap.class));


            //post
            LinkedHashMap<String, Object> postMap = xmlMapper.readValue(post, LinkedHashMap.class);
            List<LinkedHashMap<String, Object>> postArray = (List<LinkedHashMap<String, Object>>) postMap.get("row");




        int todosPointer=0;
        int postPointer=0;

        List<LinkedHashMap<String, Object>> userCombined = new ArrayList<>();

        for (int i = 0; i < userArray.size(); i++) {
            LinkedHashMap<String, Object> userObj = userArray.get(i);
            int userId = (int) userObj.get("id");

            //  todos
            List<LinkedHashMap<String, Object>> todosNewList = new ArrayList<>();
            for (;todosPointer < todosArray.size();todosPointer++) {
                LinkedHashMap<String, Object> todoObj = todosArray.get(todosPointer);
                if ((int) todoObj.get("userId") != userId) {
                    break; // todos sudah ordered jadi stop kalau udah lebih dari userid
                } else {
                    todosNewList.add(todoObj);
                }
            }


            userObj.put("todos", todosNewList);




            //post
            List<LinkedHashMap<String, Object>> postsNewList = new ArrayList<>();
            for (;postPointer < postArray.size();postPointer++) {
                LinkedHashMap<String, Object> postObj = postArray.get(postPointer);
                if (Integer.parseInt(postObj.get("userId").toString()) != userId) {
                    break;  // todos sudah ordered jadi stop kalau udah lebih dari userid
                } else {
                    postsNewList.add(postObj);
                }
            }

            userObj.put("posts", postsNewList);


            //tambahkan kembali ke hasil
            userCombined.add(userObj);


        }



        return mapper.writeValueAsString(userCombined);


    }
}
