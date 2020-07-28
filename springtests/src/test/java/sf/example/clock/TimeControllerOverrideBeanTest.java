package sf.example.clock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimeController.class)
@TestPropertySource(properties = {
        "spring.main.allow-bean-definition-overriding=true"
})
class TimeControllerOverrideBeanTest {

    @TestConfiguration
    static class TestClockConfiguration {
        @Bean
        public Clock clock() {
            LocalDateTime localDateTime =
                    LocalDateTime.of(2019, 7, 29, 14, 10, 53);
            return Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnCurrentTime() throws Exception {
        mockMvc.perform(get("/now"))
                .andExpect(status().isOk())
                .andExpect(content().string("2019-07-29 14:10:53"));
    }
}
