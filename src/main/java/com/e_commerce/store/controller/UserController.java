package com.e_commerce.store.controller;

import com.e_commerce.store.dto.UserDTO;
import com.e_commerce.store.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create user API
    @PostMapping(value = "/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    // Get user by ID API
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserByid(id), HttpStatus.OK);
    }

    // Update user API
    @PutMapping(value = "/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
    }

    // Delete user by ID API
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all users API
    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();  // Returning a list of UserDTO
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}