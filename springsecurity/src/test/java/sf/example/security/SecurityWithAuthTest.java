package sf.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles(profiles = "production")
@TestPropertySource(properties = { //
        "de.andrena.springbootadvancedtesting.username=testuser",
        "de.andrena.springbootadvancedtesting.password=testpasswd" })
class SecurityWithAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void http4xxWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api")) //
                .andExpect(status().is4xxClientError());
    }

    @Test
    void http4xxWithWrongAuthentication() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("testuser", "WRONG");

        mockMvc.perform(get("/api").headers(httpHeaders)) //
                .andExpect(status().is4xxClientError());
    }

    @Test
    void http2xxWithRightAuthentication() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("testuser", "testpasswd");

        mockMvc.perform(get("/api").headers(httpHeaders)) //
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void http2xxWithRightAuthentication_withPostprocessor() throws Exception {
        mockMvc.perform(get("/api") //
                .with(SecurityMockMvcRequestPostProcessors.user("testuser"))) //
                .andExpect(status().is2xxSuccessful());
    }

}
