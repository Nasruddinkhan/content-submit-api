package com.mypractice.content.api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.mypractice.content.api.document.ContentDocument;

@Repository
public interface ContentRepository extends ReactiveMongoRepository<ContentDocument, String> {


}
