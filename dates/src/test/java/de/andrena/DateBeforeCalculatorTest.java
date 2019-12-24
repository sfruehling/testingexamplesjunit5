package de.andrena;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DateBeforeCalculatorTest {
    @Test
    public void calculatesJanForFeb() {
        DateBeforeCalculator dateBeforeCalculator = setupDateBeforeCalculatorForMonth(Month.FEBRUARY);
        assertThat(dateBeforeCalculator.calculateMonthBeforeToday(), is(Month.JANUARY));
    }


    @SuppressWarnings("SameParameterValue")
    private DateBeforeCalculator setupDateBeforeCalculatorForMonth(Month month) {
        LocalDateTime currentDate = LocalDateTime.of(2010, month.getValue(),1,0,1);
        Clock clock = Clock.fixed(currentDate.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        return new DateBeforeCalculator(clock);
    }
}
