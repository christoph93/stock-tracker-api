package com.stoncks.repository;

import com.stoncks.document.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {


    Optional<List<Portfolio>> findByOwner(String owner);

    @Query("{ 'owner' : ?0 , 'name' : ?1}")
    Optional<Portfolio> findByOwnerAndName(String owner, String name);

}
