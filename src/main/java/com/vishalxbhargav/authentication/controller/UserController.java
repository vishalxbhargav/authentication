package com.vishalxbhargav.authentication.controller;

import com.vishalxbhargav.authentication.model.User;
import com.vishalxbhargav.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @GetMapping("user")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }
    @GetMapping("user/profile")
    public User getUserById(@RequestHeader("Authorization") String token) throws Exception {
        User user=userService.getUserByJwtToken(token);
        return userService.getUser(user.getId());
    }
    @DeleteMapping("user/{userId}")
    public boolean deleteUser(@PathVariable ObjectId userId) throws Exception {
        User user=userService.getUser(userId);
        if(user!=null){
            userService.deleteUser(userId);
            return true;
        }
        return false;
    }
    @GetMapping("api/user")
    public String chech(){
        return "this is api route";
    }
    @PutMapping("user/update")
    public User updateUser(@RequestBody User user,@RequestHeader("Authorization")String token) throws Exception {
        User reqUser=userService.getUserByJwtToken(token);
        return userService.updateUser(user,reqUser.getId());
    }
    @GetMapping("user/email/{email}")
    public User findUserByEamil(@PathVariable String email){
        return userService.findbyEmail(email);
    }
}
