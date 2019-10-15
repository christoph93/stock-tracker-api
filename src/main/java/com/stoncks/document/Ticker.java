package com.stoncks.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
public class Ticker {

    @Id
    private String id;

    private String content;
    private long createDate;
    private String symbol;

    public Ticker(String content, long createDate) {
        this.content = content;
        this.createDate = createDate;
        this.symbol = updateSymbol();
    }

    public Ticker(){

    }


    public String updateSymbol(){

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(content, Map.class);
            System.out.println(map);


            Map<String, Object> meta = (Map<String, Object>) map.get("Meta Data");
            System.out.println(meta);
            String s = (String) meta.get("2 Symbol");

            return s;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return "ERROR";
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
