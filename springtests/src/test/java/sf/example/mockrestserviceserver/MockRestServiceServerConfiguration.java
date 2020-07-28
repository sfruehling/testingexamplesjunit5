package sf.example.mockrestserviceserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockRestServiceServerConfiguration {

    @Bean
    DetailsServiceClient getClientService(RestTemplateBuilder restTemplateBuilder) {
        return new DetailsServiceClient(restTemplateBuilder);
    }
}
