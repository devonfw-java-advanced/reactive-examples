package com.capgemini.training.reactive.mongo.repository;

import com.capgemini.training.reactive.mongo.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
