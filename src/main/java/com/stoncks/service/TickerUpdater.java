package com.stoncks.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stoncks.document.Ticker;
import com.stoncks.document.Transaction;
import com.stoncks.io.TickerFromUrl;
import com.stoncks.repository.TickerRepository;
import com.stoncks.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TickerUpdater {


    public void updateFromTransactions(TransactionRepository transactionRepository, TickerRepository tickerRepository, int limitPerMin) throws InterruptedException {

        long firstReq = 0;
        int reqCount = 0;
        long intervalInSec;

        List<Transaction> transactions = transactionRepository.findAll();


        HashSet<String> symbols = new HashSet<>();

        for(Transaction t : transactions){
            symbols.add(t.getSymbol());
        }

        System.out.println("Symbols to update");
        System.out.println(symbols);

        TickerFromUrl tfu = new TickerFromUrl();

        for(String s : symbols){

            if(reqCount == 0) firstReq = System.currentTimeMillis();

            intervalInSec = (System.currentTimeMillis() - firstReq)/1000;
            System.out.println("Current interval from firstReq: " + intervalInSec);

            while(reqCount == 5 && intervalInSec < 60) {
                intervalInSec = (System.currentTimeMillis() - firstReq)/1000;
                System.out.println("Reached 5 reqs. Waiting for: " + (60-intervalInSec));
                Thread.sleep(5000);
            }

            if(reqCount == 5){
                reqCount = 0;
                firstReq = System.currentTimeMillis();
            }

            //delete all ticker for that symbol, in case there us more than one
            List<Ticker> tickers = tickerRepository.deleteBySymbol(s + ".SAO");
            if(tickers != null) {
                for (Ticker t : tickers) {
                    System.out.println("Deleting ticker " + t.getSymbol());
                }
            }

            Ticker temp = tfu.tickerDaily(s + ".SAO");

            System.out.println("Saving Ticker " + temp.getSymbol());
            if (temp != null) tickerRepository.save(temp);
            Thread.sleep(1000);
            reqCount++;
            }

        //tickerRepository.saveAll(tickers);

/*
        TickerFromUrl ticker = new TickerFromUrl();
        JsonNode tickerAsJson = ticker.tickerDaily("PETR4.SAO");
        Ticker t = new Ticker(tickerAsJson, System.currentTimeMillis());

        tickerRepository.deleteAll();
        tickerRepository.save(t);*/


    }





}
