package com.example.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithEmbeddedPostgres
class PersonControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void list_shouldRenderListOfKnownPersonNames() {
        jdbcTemplate.execute("TRUNCATE TABLE person");
        jdbcTemplate.execute("INSERT INTO person (name) VALUES ('Test'), ('Ein'), ('Welt'), ('Hallo')");

        String content = restTemplate.getForObject("http://localhost:" + port + "/person", String.class);

        assertThat(content)
                .isEqualTo("Test,Ein,Welt,Hallo");
    }
}