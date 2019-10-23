package com.stoncks.io;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stoncks.document.Ticker;

import java.util.HashMap;
public class PriceReader {

    private Ticker ticker;
    private HashMap<String, JsonElement> timeSeries;

    public PriceReader(Ticker ticker) {
        this.ticker = ticker;
        this.timeSeries = readTimeSeries();
    }

    public HashMap<String, JsonElement> readTimeSeries () {
        HashMap<String, JsonElement> timeSeriesMap = new HashMap<>();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(gson.toJson(this.ticker.getContent()), JsonObject.class);

        JsonObject timeSeriesDaily = jsonObject.getAsJsonObject("Time Series (Daily)");

        for(String s : timeSeriesDaily.keySet()){
            timeSeriesMap.put(s,timeSeriesDaily.get(s));
        }
        return timeSeriesMap;

    }

    public double getPriceByDate(String date, String type){
        JsonObject prices = this.timeSeries.get(date).getAsJsonObject();

        return Double.parseDouble(prices.get(type).getAsString());
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
