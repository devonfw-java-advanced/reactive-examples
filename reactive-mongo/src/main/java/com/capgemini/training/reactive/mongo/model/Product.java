package com.capgemini.training.reactive.mongo.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("product")
public class Product {

    private String id;
    private String name;
    private Double price;
}

