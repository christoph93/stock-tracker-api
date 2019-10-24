package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stoncks.document.Ticker;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface TickerRepository extends MongoRepository<Ticker, String>{


    List<Ticker> findBySymbol(String symbol);
    List<Ticker> deleteBySymbol(String symbol);
    List<Ticker> findByCreateDateBefore(Date date);
    List<Ticker> findByCreateDateAfter(Date date);


}
