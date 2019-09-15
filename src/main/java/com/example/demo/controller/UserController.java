package com.example.demo.controller;

import java.io.IOException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController {
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String test() {
		return "succes";
	}

	@PostMapping("/user")
	public ResponseEntity creteUser(@RequestBody User user) throws IOException {
		return new ResponseEntity(userService.createUser(user), HttpStatus.CREATED);
	}

	@PutMapping("/user")
	public ResponseEntity updateUser(@RequestBody User user) throws IOException {
		return new ResponseEntity(userService.updateUser(user), HttpStatus.OK);
	}
	@DeleteMapping("/user")
	public ResponseEntity deleteUser(@RequestParam String id) throws IOException {
		String status = userService.deleteUserById(id);
		if(userService.CANT_FOUND.equals(status)) {
			return new ResponseEntity(status, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(userService.deleteUserById(id), HttpStatus.OK);
	}
	@GetMapping("/user")
	public ResponseEntity findUser(@RequestParam String id) throws IOException {
		User user = userService.findUserById(id);
		if(null == user) {
			return new ResponseEntity(id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(user, HttpStatus.OK);
	}
	public ResponseEntity findUsers(@RequestParam int skip, @RequestParam int take) {
		
	}
}
