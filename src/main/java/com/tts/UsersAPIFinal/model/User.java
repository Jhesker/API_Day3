package com.tts.UsersAPIFinal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

@Entity
public class User {

	// Field level attributes that define my User Class
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Length(max = 20, message = "First Name Needs to be less than 20 Characters")
	private String firstName;

	@Length(min = 2, message = "Last Name Needs to be Longer than 2 Characters")
	private String lastName;

	@Size(min = 4, max = 20, message = "Please use the correct Full Name of the State")
	private String state;

	// No args constructor "Default" Constructor
	public User() {
	}

	// Constructor Will attributes.
	public User(Long id, String firstName, String lastName, String state) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.state = state;
	}

	// Getters/Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// To String method
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ",\n lastName=" + lastName + ", state=" + state + "]\n";
	}
}
