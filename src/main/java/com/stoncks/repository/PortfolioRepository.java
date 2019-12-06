package com.stoncks.repository;

import com.stoncks.document.PortfolioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends MongoRepository<PortfolioDocument, String> {


    Optional<List<PortfolioDocument>> findByOwner(String owner);

    @Query("{ 'owner' : ?0 , 'name' : ?1}")
    Optional<PortfolioDocument> findByNameAndOwner(String owner, String name);

}
