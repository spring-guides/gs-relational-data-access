package hello;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {
    	
        System.out.println("Creating tables");
        
        jdbcTemplate.execute("drop table customers if exists");
        jdbcTemplate.execute("create table customers(" +
                "id serial, first_name varchar(255), last_name varchar(255))");

        List<String> fullNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long");
        
        fullNames.stream()
        	.map(name -> name.split(" "))
        	.map(names -> { System.out.printf("Inserting customer record for %s %s\n", names[0], names[1]); return names; })
        	.forEach(names ->  jdbcTemplate.update("INSERT INTO customers(first_name,last_name) values(?,?)", names[0], names[1]));

        System.out.println("Querying for customer records where first_name = 'Josh':");
        
        jdbcTemplate.query(
                "select id, first_name, last_name from customers where first_name = ?", new Object[] { "Josh" },
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")))
        .forEach(System.out::println);
    }
}