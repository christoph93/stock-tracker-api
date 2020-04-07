package com.stocktrackerapi.io;



import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stocktrackerapi.document.Symbol;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PriceReader {

    private Symbol symbol;
    private HashMap<String, JsonElement> timeSeries;
    private double lastPrice;
    private Date lastPriceDate;

    public PriceReader(Symbol symbol, String timeSeriesText) {
        this.symbol = symbol;
        this.timeSeries = readTimeSeries(timeSeriesText);
    }

    public HashMap<String, JsonElement> readTimeSeries (String timeSeriesText) {
        HashMap<String, JsonElement> timeSeriesMap = new HashMap<>();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(gson.toJson(this.symbol.getFullContent()), JsonObject.class);

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

    public Map<Date, Double> getPricesAsMap() throws ParseException {
        lastPrice = -1;
        lastPriceDate = null;
        HashMap<Date, Double> datePriceMap = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        for(String s : getTimeSeries().keySet()){
            datePriceMap.put(formatter.parse(s), getPriceByDate(s, "4. close"));
        }

         ArrayList<Date> datesList = new ArrayList<>(datePriceMap.keySet());
         Collections.sort(datesList);
         lastPriceDate = datesList.get(datesList.size()-1);
         lastPrice = datePriceMap.get(lastPriceDate);

            /* print ordered */
            /*
            closingPricesLocalDate.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Date, Double>comparingByKey())
                    .forEach( System.out::println);
                    */

        return datePriceMap;
    }

    public Symbol setClosingPrices() throws ParseException {
        this.symbol.setClosingPrices(getPricesAsMap());
        this.symbol.setLastPrice(lastPrice);
        this.symbol.setLastPriceDate(lastPriceDate);
        return this.symbol;
    }


    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public HashMap<String, JsonElement> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(HashMap<String, JsonElement> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
