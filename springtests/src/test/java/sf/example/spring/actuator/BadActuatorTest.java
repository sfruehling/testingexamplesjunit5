package sf.example.spring.actuator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "management.endpoint.health.show-details=always"
})
public class BadActuatorTest {

    @Autowired
    MockMvc mockMvc;

    @TestConfiguration
    static class BadActuatorTestConfiguration {
        @Bean
        public HealthIndicator getTestHealthIndicator() {
            return () -> Health.down().withDetail("TestHealthIndicator always returns false", 1).build();
        }
    }

    @Test
    void healthEndpointReturns5xxOnHealthDown() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/actuator/health")
        )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }



}

