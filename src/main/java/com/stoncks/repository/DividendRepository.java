package com.stoncks.repository;

import com.stoncks.document.Dividend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DividendRepository extends MongoRepository<Dividend, String>{


    Optional<List<Dividend>> findBySymbol(String symbol);


    }
