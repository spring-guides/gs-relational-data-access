package com.example.relationaldataaccess;

public class Customer {
	private final long id;
	private final String firstName, lastName;

	public Customer(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%d, firstName='%s', lastName='%s']",
				id, firstName, lastName);
	}

	// getters & setters omitted for brevity
}
