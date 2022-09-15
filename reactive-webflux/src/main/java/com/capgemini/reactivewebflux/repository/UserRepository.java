package com.capgemini.reactivewebflux.repository;

import com.capgemini.reactivewebflux.documents.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
