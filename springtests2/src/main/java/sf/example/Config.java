package sf.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Configuration
public class Config {

    @Bean
    public Clock clock() {
        LocalDateTime localDateTime =
                LocalDateTime.of(2019, 7, 29, 14, 10, 53);
        return Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    }
}
