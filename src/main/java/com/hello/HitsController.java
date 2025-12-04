package com.hello;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HitsController {

    private final JdbcTemplate jdbcTemplate;

    public HitsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/hits")
    public String hits() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM hits", Integer.class
        );

        if (count == null || count == 0) {
            jdbcTemplate.update("INSERT INTO hits (value) VALUES (1)");
        } else {
            jdbcTemplate.update("UPDATE hits SET value = value + 1 WHERE id = 1");
        }

        Integer current = jdbcTemplate.queryForObject(
                "SELECT value FROM hits WHERE id = 1", Integer.class
        );

        return "Hits: " + current;
    }
}
