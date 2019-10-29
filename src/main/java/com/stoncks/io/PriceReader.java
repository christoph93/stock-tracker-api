package com.stoncks.io;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stoncks.document.Ticker;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PriceReader {

    private Ticker ticker;
    private HashMap<String, JsonElement> timeSeries;

    public PriceReader(Ticker ticker, String timeSeriesText) {
        this.ticker = ticker;
        this.timeSeries = readTimeSeries(timeSeriesText);
    }

    public HashMap<String, JsonElement> readTimeSeries (String timeSeriesText) {
        HashMap<String, JsonElement> timeSeriesMap = new HashMap<>();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(gson.toJson(this.ticker.getFullContent()), JsonObject.class);

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

    public Ticker setClosingPrices(){
        this.ticker.setClosingPrices(getPricesAsMap());
        return this.ticker;
    }


    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public HashMap<String, JsonElement> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(HashMap<String, JsonElement> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
