package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stoncks.document.Ticker;
import java.util.List;

public interface TickerRepository extends MongoRepository<Ticker, String>{


    List<Ticker> findBySymbol(String symbol);
    List<Ticker> deleteBySymbol(String symbol);


}
