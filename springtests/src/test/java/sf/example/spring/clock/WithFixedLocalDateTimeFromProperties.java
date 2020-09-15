package sf.example.spring.clock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.lang.annotation.Retention;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Import(WithFixedLocalDateTimeFromProperties.WithFixedLocalDateTimeConfiguration.class)
public @interface WithFixedLocalDateTimeFromProperties {

    @TestConfiguration
    class WithFixedLocalDateTimeConfiguration {

        @Value("${date}")
        String date;

        @Value("${time}")
        String time;

        @Bean
        @Primary
        public Clock replaceClock() {
            LocalDateTime localDateTime = localDateTimeFromProperties();
            return fixedClock(localDateTime);
        }

        private LocalDateTime localDateTimeFromProperties() {
            String dateString = this.date + " " + this.time;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateString, dateTimeFormatter);
        }

        private Clock fixedClock(LocalDateTime localDateTime) {
            return Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        }
    }



}
