package com.stoncks.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Ticker {

    @Id
    private String id;

    private Object content;
    private Date createDate;
    private String symbol;

    public Ticker(Object content, Date createDate, String symbol) {
        this.content = content;
        this.createDate = createDate;
        this.symbol = symbol;
    }

    public Ticker(){

    }


    public String updateSymbol(){

        /*
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(content, Map.class);
            Map<String, Object> meta = (Map<String, Object>) map.get("Meta Data");
            String s = (String) meta.get("2. Symbol");

            return s;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "ERROR";*/
        return "TEST";
    }

    public String getId() {
        return id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
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
