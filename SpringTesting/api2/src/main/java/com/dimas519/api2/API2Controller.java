package com.dimas519.api2;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


@RestController
public class API2Controller {
    private JdbcTemplate jdbcPostgresql;

    public API2Controller(JdbcTemplate jdbcTemplate){
        this.jdbcPostgresql = jdbcTemplate;
    }

    private List<String> getData(){
        String sql = "select post from public.posts";
        return jdbcPostgresql.queryForList(sql, String.class);
    }

    private String jsonToXML(String json){

        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode node = jsonMapper.readTree(json);


            ObjectNode wrapper = jsonMapper.createObjectNode();
            wrapper.set("row", node);//



            XmlMapper xmlMapper = new XmlMapper();

            String xml = xmlMapper.writer()
                    .withRootName("root")   // kasih root element custom
                    .writeValueAsString(wrapper);

            return xml;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @GetMapping(value="/posts",produces="text/xml")
    public String getPost(){
        List<String> data=getData();

        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+jsonToXML(data.get(0));


        return xml;
    }






}
