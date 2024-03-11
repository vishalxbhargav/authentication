package com.vishalxbhargav.authentication.service;

import com.vishalxbhargav.authentication.config.JwtProvider;
import com.vishalxbhargav.authentication.model.User;
import com.vishalxbhargav.authentication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImplementation implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User newUserCreate(User user){
        return userRepository.save(user);
    }
    @Override
    public User getUser(ObjectId userId) throws Exception {
        Optional<User> usr= userRepository.findById(userId);
        if(usr.isEmpty()) throw new Exception("User not find with "+userId);
        return usr.get();
    }

    @Override
    public User updateUser(User user, ObjectId userId) throws Exception {
        User oldUser=getUser(userId);
        if(user.getFirstName()!=null) oldUser.setFirstName(user.getFirstName());
        if(user.getLastName()!=null) oldUser.setLastName(user.getLastName());
        if(user.getEmail()!=null) oldUser.setEmail(user.getEmail());
        if(user.getUsername()!=null) oldUser.setUsername(user.getUsername());
        if(user.getPassword()!=null) oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return newUserCreate(user);
    }

    @Override
    public User deleteUser(ObjectId userId) throws Exception {
        User user=getUser(userId);
        userRepository.delete(user);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByJwtToken(String jwt) {
        String email=JwtProvider.getEmailFromJwtToken(jwt);
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findbyEmail(String username) {
        return userRepository.findUserByEmail(username);
    }

}
