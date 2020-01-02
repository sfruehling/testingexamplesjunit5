package de.andrena;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class ParameterizedDemoTest {

    @ParameterizedTest
    @MethodSource("demoInputSource")
    void readsWeekdayCorrect(String input, int result) {

        assertThat(Integer.parseInt(input), is(result));
    }

    private static Stream<Arguments> demoInputSource() {
        return Stream.of(
                Arguments.of("1", 1),
                Arguments.of("2", 2)
        );
    }

}