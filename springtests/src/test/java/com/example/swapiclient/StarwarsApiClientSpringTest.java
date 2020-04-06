package com.example.swapiclient;

import com.example.builders.StarwarsApiListFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(StarwarsApiClient.class)
class StarwarsApiClientSpringTest {

	@Autowired
	private StarwarsApiClient swApiClient;

	@Autowired
	private MockRestServiceServer server;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void whenResultEmpty_cannotFindStarship() throws JsonProcessingException {
		setupServerWithStarships(new StarwarsApiList<>());

		Optional<StarwarsStarship> result = swApiClient.findFirst(starship -> true);

		assertThat(result).isEmpty();
	}

	@Test
	void whenResultNotEmpty_canFindStarship() throws JsonProcessingException {
		setupServerWithAStarship();

		Optional<StarwarsStarship> result = swApiClient.findFirst(starship -> true);

		assertThat(result).isNotEmpty();
	}

	@Test
	void whenResultNotEmpty_filtersOutWrongStarships() throws JsonProcessingException {
		setupServerWithAStarship();

		Optional<StarwarsStarship> result = swApiClient.findFirst(starship -> false);

		assertThat(result).isEmpty();
	}

	@AfterEach
	void verify() {
		server.verify();
	}

	private void setupServerWithAStarship() throws JsonProcessingException {
		StarwarsApiList<StarwarsStarship> aStarwarsStarship = new StarwarsApiListFactory().getAStarwarsStarship();
		setupServerWithStarships(aStarwarsStarship);
	}

	private void setupServerWithStarships(StarwarsApiList<StarwarsStarship> starwarsApiList)
			throws JsonProcessingException {
		String body = objectMapper.writeValueAsString(starwarsApiList);

		server.expect(method(GET)) //
				.andExpect(requestTo(containsString("starships"))) //
				.andExpect(header(ACCEPT, containsString(APPLICATION_JSON_VALUE))) //
				.andRespond(withSuccess(body, APPLICATION_JSON));
	}

	// TODO refactor test -> extract duplication
	// TODO test paging

}
