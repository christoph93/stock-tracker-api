package com.stoncks.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stoncks.document.Ticker;
import com.stoncks.document.Transaction;
import com.stoncks.io.TickerFromUrl;
import com.stoncks.repository.TickerRepository;
import com.stoncks.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

public class TickerUpdater {


    public void updateFromTransactions(TransactionRepository transactionRepository, TickerRepository tickerRepository, int limitPerMin){

        List<Transaction> transactions = transactionRepository.findAll();

        HashSet<String> symbols = new HashSet<>();

        for(Transaction t : transactions){
            symbols.add(t.getSymbol());
        }

       /* System.out.println("List of symbols in hashset and db:");
        for(String s : symbols){
            tickerRepository.findBySymbol(s);
        }*/



/*
        TickerFromUrl ticker = new TickerFromUrl();
        JsonNode tickerAsJson = ticker.tickerDaily("PETR4.SAO");
        Ticker t = new Ticker(tickerAsJson, System.currentTimeMillis());

        tickerRepository.deleteAll();
        tickerRepository.save(t);*/


    }





}
