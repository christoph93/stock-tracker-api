package com.stoncks.repository;

import com.stoncks.document.Alias;
import com.stoncks.document.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AliasRepository extends MongoRepository<Alias, String>{


    Optional<List<Alias>> findByAlias(String symbol);


    }
