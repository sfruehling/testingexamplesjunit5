package sf.example.clock;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.Retention;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.platform.commons.util.AnnotationUtils.findAnnotation;
import static org.springframework.test.context.junit.jupiter.SpringExtension.getApplicationContext;

@Retention(RUNTIME)
@ExtendWith(WithLocalDateTime.WithLocalDateTimeExtension.class)
@TestPropertySource(properties = {
        "spring.main.allow-bean-definition-overriding=true"
})
@ImportAutoConfiguration(WithLocalDateTime.ClockConfiguration.class)
public @interface WithLocalDateTime {
    String date();

    String time();

    class WithLocalDateTimeExtension implements BeforeEachCallback, AfterEachCallback {
        private static final ExtensionContext.Namespace EXTENSION_SCOPE = ExtensionContext.Namespace.create(WithLocalDateTimeExtension.class);
        private static final Class<DelegatingClock> DELEGATING_CLOCK_KEY = DelegatingClock.class;

        @Override
        public void beforeEach(ExtensionContext extensionContext) {
            findAnnotation(extensionContext.getTestClass(), WithLocalDateTime.class)
                    .ifPresent((annotation) -> setClockTo(extensionContext, annotation));
        }

        @Override
        public void afterEach(ExtensionContext extensionContext) {
            findAnnotation(extensionContext.getTestClass(), WithLocalDateTime.class)
                    .ifPresent((annotation) -> resetClockFrom(extensionContext));
        }

        private void setClockTo(ExtensionContext extensionContext, WithLocalDateTime withLocalDateTimeAnnotation) {
            DelegatingClock delegatingClock = delegatingClockFrom(extensionContext);

            Clock clock = delegatingClock.getClock();
            extensionContext.getStore(EXTENSION_SCOPE).put(DELEGATING_CLOCK_KEY, clock);

            LocalDateTime localDateTime = localDateTimeFrom(withLocalDateTimeAnnotation);
            Clock fixedClock = fixedClock(localDateTime);
            delegatingClock.setClock(fixedClock);
        }

        private void resetClockFrom(ExtensionContext extensionContext) {
            DelegatingClock delegatingClock = delegatingClockFrom(extensionContext);
            Clock clock = (Clock) extensionContext.getStore(EXTENSION_SCOPE).remove(DELEGATING_CLOCK_KEY);
            delegatingClock.setClock(clock);
        }

        private DelegatingClock delegatingClockFrom(ExtensionContext extensionContext) {
            Clock clock = getApplicationContext(extensionContext).getBean(Clock.class);
            if (!(clock instanceof DelegatingClock)) {
                throw new IllegalStateException("clock '" + clock + "' must be of type '" + DelegatingClock.class.getName() + "'");
            }

            return (DelegatingClock) clock;
        }

        private LocalDateTime localDateTimeFrom(WithLocalDateTime annotation) {
            String dateString = annotation.date() + " " + annotation.time();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateString, dateTimeFormatter);
        }

        private Clock fixedClock(LocalDateTime localDateTime) {
            return Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        }
    }

    class DelegatingClock extends Clock {
        private Clock clock;

        public DelegatingClock(Clock clock) {
            this.clock = clock;
        }

        @Override
        public ZoneId getZone() {
            return clock.getZone();
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return clock.withZone(zone);
        }

        @Override
        public Instant instant() {
            return clock.instant();
        }

        public Clock getClock() {
            return clock;
        }

        public void setClock(Clock clock) {
            this.clock = clock;
        }
    }

    @TestConfiguration
    class ClockConfiguration {
        @Bean
        public Clock clock() {
            return new DelegatingClock(Clock.systemUTC());
        }
    }
}
