package sf.example.spring.clock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimeController.class)
@WithLocalDateTime(date="2019-07-29", time="14:10:53")
public class TimeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnCurrentTime() throws Exception {
        mockMvc.perform(get("/now"))
                .andExpect(status().isOk())
                .andExpect(content().string("2019-07-29 14:10:53"));

    }
}
