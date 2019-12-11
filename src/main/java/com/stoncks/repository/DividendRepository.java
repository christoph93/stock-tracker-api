package com.stoncks.repository;

import com.stoncks.document.Dividend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DividendRepository extends MongoRepository<Dividend, String>{


    Optional<List<Dividend>> findBySymbol(String symbol);

    @Query("{ 'symbol' : ?0 , 'payDate' : { $lt : ?1} }")
    Optional<List<Dividend>> findBySymbolAndPayDateBefore(String symbol, Date payDate);


    }
