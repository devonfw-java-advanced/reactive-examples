package com.capgemini.reactivewebflux.documents;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
public class User {

    private String id;
    private String email;

    public User(String email) {
        this.email = email;
    }
}
