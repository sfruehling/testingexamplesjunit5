package sf.example.spring.scheduling;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class SchedulingTest {

    @Test
    void testCronExpression() {
        LocalDateTime next = CronExpression.parse("1 * * * * *")
                .next(LocalDateTime.of(2021, 1, 1, 0, 0, 0));

        assertThat(next).isEqualTo(LocalDateTime.of(2021,1,1,0,0,1));
    }
}
