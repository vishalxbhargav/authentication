package com.vishalxbhargav.authentication.repository;

import com.vishalxbhargav.authentication.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    public User getUserById(Integer userId);
    public User findUserByEmail(String email);
}
