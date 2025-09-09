package com.example.relationaldataaccess

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class RelationalDataAccessApplication(
    private val jdbcTemplate: JdbcTemplate
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(RelationalDataAccessApplication::class.java)

    override fun run(vararg args: String) {
        log.info("Creating tables")

        jdbcTemplate.execute("DROP TABLE IF EXISTS customers")
        jdbcTemplate.execute("""
            CREATE TABLE customers(
                id SERIAL, 
                first_name VARCHAR(255), 
                last_name VARCHAR(255)
            )
        """.trimIndent())

        // Split up the array of whole names into an array of first/last names
        val splitUpNames = listOf("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
            .map { it.split(" ").toTypedArray() }

        // Use Kotlin collection functions to print out each tuple of the list
        splitUpNames.forEach { name ->
            log.info("Inserting customer record for {} {}", name[0], name[1])
        }

        // Use JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames)

        log.info("Querying for customer records where first_name = 'Josh':")
        jdbcTemplate.query(
            "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
            { rs, _ ->
                Customer(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
                )
            },
            "Josh"
        ).forEach { customer ->
            log.info(customer.toString())
        }
    }
}

fun main(args: Array<String>) {
    runApplication<RelationalDataAccessApplication>(*args)
}