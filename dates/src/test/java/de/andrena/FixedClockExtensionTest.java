package de.andrena;

import de.andrena.extension.FixedClockExtension;
import de.andrena.extension.FixedClockExtension.FixedClockAnnotation;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(FixedClockExtension.class)
public class FixedClockExtensionTest {

    @Test
    void fixDateByAnnotation(@FixedClockAnnotation(date = "2019-02-14", time = "12:00:00") Clock clock) {
        LocalDateTime now = LocalDateTime.now(clock);

        assertThat(now.getDayOfMonth(), Matchers.is(14));
        assertThat(now.getMonth(), Matchers.is(Month.FEBRUARY));
        assertThat(now.getYear(), Matchers.is(2019));
    }
}
