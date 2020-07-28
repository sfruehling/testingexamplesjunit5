package sf.example.spring.clock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TimeController {
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Clock clock;

    @Autowired
    public TimeController(Clock clock) {
        this.clock = clock;
    }

    @GetMapping("/now")
    String now() {
        LocalDateTime now = LocalDateTime.now(clock);
        return now.format(DATE_TIME_FORMATTER);
    }
}
