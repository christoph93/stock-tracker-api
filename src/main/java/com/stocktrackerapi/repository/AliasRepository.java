package com.stocktrackerapi.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.stocktrackerapi.document.Alias;

import java.util.List;
import java.util.Optional;

public interface AliasRepository extends MongoRepository<Alias, String>{


    Optional<List<Alias>> findByAlias(String alias);
    Optional<List<Alias>> findBySymbol(String symbol);


    }
