package com.stoncks.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;

public class TickerFromUrl {


    public void readURL(String function, String symbol) {

        String apiKey = "N6UZN5PBXVO599CV";

        ////https://www.alphavantage.co/query?function=${json.apiFunction}&symbol=${json.symbol}&interval=${json.interval}&apikey=${conf.apiKey}
        String urlString = "https://www.alphavantage.co/query?function=" + function + "&symbol=" + symbol + "&apikey=" + apiKey;
        System.out.println(urlString);


        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }




}
