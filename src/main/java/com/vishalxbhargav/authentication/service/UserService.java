package com.vishalxbhargav.authentication.service;

import com.vishalxbhargav.authentication.model.User;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public User createUser(User user);
    public User getUser(ObjectId userId) throws Exception;
    public User updateUser(User user, ObjectId userId) throws Exception;
    public User deleteUser(ObjectId userId) throws Exception;
    public List<User> getAllUser();
    public User getUserByJwtToken(String jwt);
    public User findbyEmail(String username);
}
