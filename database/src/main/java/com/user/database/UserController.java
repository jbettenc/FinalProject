package com.user.database;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
class UserController {
  	private final UserRepository repository;
	

	UserController(UserRepository repository) {
		this.repository = repository;
	}

	@PostMapping("/user")
	public ResponseEntity<Integer> newUser(@RequestBody User newUser) {
		return repository.save(newUser);
	}

	@PostMapping("/user/{email}")
	public ResponseEntity<Integer> newUser(@PathVariable String email) {
		return repository.save(email);
	}

	@DeleteMapping("/user/{email}")
	public ResponseEntity<Integer> deleteUser(@PathVariable String email) {
		return repository.deleteByEmail(email);
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<Integer> deleteUser(@RequestBody User newUser) {
		return repository.delete(newUser);
	}
}