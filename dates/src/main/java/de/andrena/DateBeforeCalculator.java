package de.andrena;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;

public class DateBeforeCalculator {
    private Clock clock;

    public DateBeforeCalculator(Clock clock) {
        this.clock = clock;
    }

    Month calculateMonthBeforeToday() {
        return LocalDateTime.now(clock).getMonth().minus(1);
    }
}
