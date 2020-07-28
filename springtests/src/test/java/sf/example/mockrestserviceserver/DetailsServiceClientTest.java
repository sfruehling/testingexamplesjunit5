package sf.example.mockrestserviceserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(DetailsServiceClient.class)
@Import(MockRestServiceServerConfiguration.class)
public class DetailsServiceClientTest {

    @Autowired
    private DetailsServiceClient client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenCallingGetUserDetails_thenClientMakesCorrectCall() throws JsonProcessingException {
        setupServerToReturn(new Details("John Smith", "john"));

        Details details = this.client.getUserDetails("john");
        assertThat(details.getLogin()).isEqualTo("john");
        assertThat(details.getName()).isEqualTo("John Smith");
    }

    @AfterEach
    void verify() {
        server.verify();
    }

    private void setupServerToReturn(Details value) throws JsonProcessingException {
        String body =
                objectMapper.writeValueAsString(value);

        this.server.expect(method(GET))
                .andExpect(requestTo("/john/details"))
                .andExpect(header(ACCEPT, containsString(APPLICATION_JSON_VALUE)))
                .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));
    }
}
