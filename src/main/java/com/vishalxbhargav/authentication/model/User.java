package com.vishalxbhargav.authentication.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class User {
    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
