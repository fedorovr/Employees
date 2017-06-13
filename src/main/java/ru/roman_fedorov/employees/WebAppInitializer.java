package ru.roman_fedorov.employees;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class WebAppInitializer implements CommandLineRunner {
    private DBUtilities dbu;

    public static void main(String[] args) {
        SpringApplication.run(WebAppInitializer.class, args);
    }

    @Bean
    public DBUtilities dbUtilities(JdbcTemplate database) {
        dbu = new DBUtilities(database);
        return dbu;
    }

    @Override
    public void run(String... strings) throws Exception {
        dbu.dropTables();
        dbu.createTables();
        dbu.insertPositions();
        dbu.insertEmployees();
    }
}
