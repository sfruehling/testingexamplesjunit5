package sf.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles(profiles = "local")
public class SecurityLocalProfileWithoutAuthTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void noAuthRequired() throws Exception {
        mockMvc.perform(get("/api")) //
                .andExpect(status().is2xxSuccessful());
    }
}
