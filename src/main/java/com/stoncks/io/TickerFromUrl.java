package com.stoncks.io;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stoncks.document.SymbolDocument;

import java.io.IOException;

import java.io.InputStreamReader;
import java.net.*;
import java.sql.Date;
import java.time.Instant;

public class TickerFromUrl {

    private String apiKey;
    private String timeSerires;

    public TickerFromUrl (String timeSeries, String apiKey){
        this.apiKey = apiKey;
        this.timeSerires = timeSeries;
    }


    public SymbolDocument getTicker(String symbol, String outputSize) {

        ////https://www.alphavantage.co/query?function=${json.apiFunction}&symbol=${json.symbol}&interval=${json.interval}&apikey=${conf.apiKey}
        String urlString = "https://www.alphavantage.co/query?function="+ timeSerires + "&symbol=" + symbol + "&outputsize=" + outputSize + "&apikey=" + apiKey;
        System.out.println(urlString);

        /*Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("websurfing1-htl1.esi.adp.com", Integer.parseInt("8080")));
        Authenticator authenticator = new Authenticator() {

            public PasswordAuthentication getPasswordAuthentication() {
                return (new PasswordAuthentication("ccalifi", "27.0tres.9tresA5".toCharArray()));
            }
        };
        Authenticator.setDefault(authenticator);*/

        //ESI proxy
        /*Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("usproxy.es.oneadp.com", Integer.parseInt("8080")));
        Authenticator authenticator = new Authenticator() {

            public PasswordAuthentication getPasswordAuthentication() {
                return (new PasswordAuthentication("ESIccalifi", "27.0tres.9tresA8".toCharArray()));
            }
        };
        authenticator.setDefault(authenticator);
        */

        try {
            URL url = new URL(urlString);
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            InputStreamReader inr = new InputStreamReader(conn.getInputStream());

            JsonObject jsonObject = new Gson().fromJson(inr, JsonObject.class);

            Object object = new Gson().fromJson(jsonObject.toString(), Object.class);


            if(!jsonObject.has("Meta Data")){
                System.out.println("Response did not contain Meta Data!");
            } else {
                return new SymbolDocument(object, Date.from(Instant.now().minusSeconds(10800)), symbol);
            }
            conn.disconnect();

            return null;

        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;

    }




}
