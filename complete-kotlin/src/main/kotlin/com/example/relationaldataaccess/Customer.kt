package com.example.relationaldataaccess

data class Customer(
    val id: Long,
    val firstName: String,
    val lastName: String
) {
    override fun toString(): String =
        "Customer[id=$id, firstName='$firstName', lastName='$lastName']"
}