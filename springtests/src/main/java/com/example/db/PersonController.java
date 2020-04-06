package com.example.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/person")
    public String getPersons() {
        List<String> persons = jdbcTemplate.query(
                "SELECT * FROM person",
                (rs, rowNum) -> rs.getString("name"));
        return String.join(",", persons);
    }
}
