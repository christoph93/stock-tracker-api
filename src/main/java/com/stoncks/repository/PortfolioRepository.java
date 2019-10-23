package com.stoncks.repository;

import com.stoncks.document.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {


    List<Portfolio> findByOwner(String owner);

    @Query("{ 'name' : ?0 , 'owner' : ?1}")
    Portfolio findByNameAndOwner(String name, String owner);

}
