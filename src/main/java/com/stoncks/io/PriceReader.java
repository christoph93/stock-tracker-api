package com.stoncks.io;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stoncks.document.TickerDocument;

import java.util.HashMap;
import java.util.Map;

public class PriceReader {

    private TickerDocument tickerDocument;
    private HashMap<String, JsonElement> timeSeries;

    public PriceReader(TickerDocument tickerDocument, String timeSeriesText) {
        this.tickerDocument = tickerDocument;
        this.timeSeries = readTimeSeries(timeSeriesText);
    }

    public HashMap<String, JsonElement> readTimeSeries (String timeSeriesText) {
        HashMap<String, JsonElement> timeSeriesMap = new HashMap<>();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(gson.toJson(this.tickerDocument.getFullContent()), JsonObject.class);

        JsonObject timeSeriesDaily = jsonObject.getAsJsonObject(timeSeriesText);

        for(String s : timeSeriesDaily.keySet()){
            timeSeriesMap.put(s,timeSeriesDaily.get(s));
        }
        return timeSeriesMap;
    }

    public double getPriceByDate(String date, String type){
        JsonObject prices = this.timeSeries.get(date).getAsJsonObject();

        return Double.parseDouble(prices.get(type).getAsString());
    }

    public Map<String, Double> getPricesAsMap(){
        HashMap<String, Double> datePriceMap = new HashMap<>();
        for(String s : getTimeSeries().keySet()){
            datePriceMap.put(s, getPriceByDate(s, "4. close"));
        }
        return datePriceMap;
    }

    public TickerDocument setClosingPrices(){
        this.tickerDocument.setClosingPrices(getPricesAsMap());
        return this.tickerDocument;
    }


    public TickerDocument getTickerDocument() {
        return tickerDocument;
    }

    public void setTickerDocument(TickerDocument tickerDocument) {
        this.tickerDocument = tickerDocument;
    }

    public HashMap<String, JsonElement> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(HashMap<String, JsonElement> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
