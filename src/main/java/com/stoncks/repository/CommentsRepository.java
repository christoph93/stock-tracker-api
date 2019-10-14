package com.stoncks.repository;

import com.stoncks.document.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentsRepository extends MongoRepository<Comments, String> {

    public List<Comments> findAll();


}
