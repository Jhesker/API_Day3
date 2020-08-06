package com.tts.UsersAPIFinal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tts.UsersAPIFinal.model.User;
import com.tts.UsersAPIFinal.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "userlist")
@RestController
@RequestMapping(value = "/v2")
public class UserControllerV2 {

	@Autowired
	private UserRepository userRepository;

	@ApiOperation(value = "Get all users in the DB", response = User.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved a list of users"),
			@ApiResponse(code = 400, message = "No user list retrieved without a 'State'") })
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(@RequestParam(value = "state", required = false) String state) {

		List<User> users = new ArrayList<>();

		if (state != null) {
			users = (List<User>) userRepository.findByState(state);
		} else {
			users = (List<User>) userRepository.findAll();
			return new ResponseEntity<List<User>>(users, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@ApiOperation(value = "Get a single user by an ID from the DB", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved user"),
			@ApiResponse(code = 404, message = "User was not found in the DB") })
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> getUserById(@PathVariable(value = "id") Long id) {

		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent()) {
			return new ResponseEntity<Optional<User>>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "Create a new user and add to DB", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created a new user in DB"),
			@ApiResponse(code = 400, message = "Invalid user input, user was not created") })
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		userRepository.save(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value = "Edit a user found and replaced in the DB", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited a user in DB"),
			@ApiResponse(code = 400, message = "Invalid user change input, user was not edited"),
			@ApiResponse(code = 404, message = "User to edit was not found in the DB") })
	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid User user,
			BindingResult bindingResult) {

		Optional<User> updateUser = userRepository.findById(id);

		if (!updateUser.isPresent()) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		userRepository.save(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a user found in the DB", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted a user from DB"),
			@ApiResponse(code = 404, message = "User to delete was not found in the DB") })
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable(value = "id") Long id) {

		Optional<User> deleteUser = userRepository.findById(id);

		if (!deleteUser.isPresent()) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		userRepository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
