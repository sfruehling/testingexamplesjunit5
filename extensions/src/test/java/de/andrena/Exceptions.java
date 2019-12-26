package de.andrena;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Exceptions {

    @Test
    void handleException() {
        Exception exception = assertThrows(Exception.class, () -> new Foo().foo());
        assertThat(exception.getMessage(), is("hallo welt"));
    }

    private static class Foo {
        void foo() throws Exception {
            throw new Exception("hallo welt");
        }
    }
}
