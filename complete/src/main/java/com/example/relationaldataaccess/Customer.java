package com.example.relationaldataaccess;

public record Customer(long id, String firstName, String lastName) {

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%d, firstName='%s', lastName='%s']",
				id, firstName, lastName);
	}
}
