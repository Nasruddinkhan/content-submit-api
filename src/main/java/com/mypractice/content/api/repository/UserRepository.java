package com.mypractice.content.api.repository;

import com.mypractice.content.api.document.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsername(String username);

}