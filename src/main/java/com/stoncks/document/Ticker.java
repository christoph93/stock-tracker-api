package com.stoncks.document;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Ticker {

    @Id
    private String id;

    private JsonNode content;

    public Ticker(JsonNode content) {
        this.content = content;
    }

    public JsonNode getContent() {
        return content;
    }

    public void setContent(JsonNode content) {
        this.content = content;
    }
}
