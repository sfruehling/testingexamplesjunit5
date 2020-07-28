package sf.example.logging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
public class LoggingTest {
    Logger logger = LoggerFactory.getLogger(LoggingTest.class);


    @Test
    void testSystemOut(CapturedOutput output) {
        System.out.println("output1");
        assertThat(output).contains("output1");
    }

    @Test
    void testSystemErr(CapturedOutput output) {
        System.err.println("output2");
        assertThat(output).contains("output2");
    }

    @Test
    void testLogger(CapturedOutput output) {
        logger.warn("warnMessage");
        assertThat(output).contains("warnMessage");
        assertThat(output).contains(" WARN ");
    }
}
