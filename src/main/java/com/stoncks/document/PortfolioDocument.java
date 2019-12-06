package com.stoncks.document;

import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.List;

public class PortfolioDocument {

    @Id
    private String id;

    private String name;
    private String[] symbols; //list of symbols
    private String owner;


    public PortfolioDocument(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSymbols() {
        return symbols;
    }

    public void setSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", symbols=" + Arrays.toString(symbols) +
                ", owner='" + owner + '\'' +
                '}';
    }
}
