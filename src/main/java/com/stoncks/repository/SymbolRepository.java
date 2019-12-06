package com.stoncks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stoncks.document.SymbolDocument;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SymbolRepository extends MongoRepository<SymbolDocument, String>{


    Optional<SymbolDocument> findBySymbol(String symbol);
    List<SymbolDocument> deleteBySymbol(String symbol);
    List<SymbolDocument> findByCreateDateBefore(Date date);
    List<SymbolDocument> findByCreateDateAfter(Date date);


}
