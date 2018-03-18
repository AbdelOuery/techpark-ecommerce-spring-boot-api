package com.api.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.User;
import com.api.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
    	this.userService = userService;
    }

    @GetMapping("/all")
    public Iterable<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/email/'{email}'")
    public User getByEmail(@PathVariable("email") String email) {
    	return userService.getByEmail(email);
    }
    
    @GetMapping("/creds/'{email}'&'{password}'")
    public String testByEmailAndPassword(@PathVariable("email") String email, @PathVariable("password") String password) {
    	return userService.testEmailAndPassword(email, password);
    }
    
    @GetMapping("/{id}")
    public User get(@PathVariable("id") Long id) {
    	return userService.getById(id);
    }
    
    @PostMapping("/create")
    public String create(@RequestBody User user) {
    	return userService.create(user);
    }
    
    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody User newValues) {
    	return userService.update(id, newValues);
    }
    
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
    	return userService.delete(id);
    }
}
