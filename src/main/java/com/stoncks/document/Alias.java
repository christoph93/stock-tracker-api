package com.stoncks.document;

public class Alias {


    private String symbol, alias;

    public Alias(String symbol, String alias) {
        this.symbol = symbol;
        this.alias = alias;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
