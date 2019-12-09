package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stoncks.document.Symbol;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SymbolRepository extends MongoRepository<Symbol, String>{


    Optional<Symbol> findBySymbol(String symbol);
    List<Symbol> deleteBySymbol(String symbol);
    List<Symbol> findByCreateDateBefore(Date date);
    List<Symbol> findByCreateDateAfter(Date date);


}
