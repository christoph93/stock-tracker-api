package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stoncks.document.TickerDocument;

import java.util.Date;
import java.util.List;

public interface TickerRepository extends MongoRepository<TickerDocument, String>{


    List<TickerDocument> findBySymbol(String symbol);
    List<TickerDocument> deleteBySymbol(String symbol);
    List<TickerDocument> findByCreateDateBefore(Date date);
    List<TickerDocument> findByCreateDateAfter(Date date);


}
