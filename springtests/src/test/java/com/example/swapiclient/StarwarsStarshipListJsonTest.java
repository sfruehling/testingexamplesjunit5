package com.example.swapiclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

@JsonTest
class StarwarsStarshipListJsonTest {

	@Autowired
	private JacksonTester<StarwarsApiList<StarwarsStarship>> json;

	@Test
	void serialization() throws IOException {
		JsonContent<StarwarsApiList<StarwarsStarship>> starshipJson = readAndWriteJson();

		assertThat(starshipJson).isStrictlyEqualToJson(jsonSample());
	}

	@Test
	void readsGenericListOfStarships() throws IOException {
		StarwarsApiList<StarwarsStarship> starshipJson = readJson();

		assertThat(starshipJson.getResults()).hasOnlyElementsOfType(StarwarsStarship.class);
	}

	@Test
	void hasCountNextPrevAndResults() throws IOException {
		JsonContent<StarwarsApiList<StarwarsStarship>> starshipJson = readAndWriteJson();

		assertThat(starshipJson).extractingJsonPathNumberValue("$.count").isNotNull();
		assertThat(starshipJson).extractingJsonPathStringValue("$.next").isNotBlank();
		assertThat(starshipJson).extractingJsonPathStringValue("$.previous").isNotBlank();
		assertThat(starshipJson).extractingJsonPathArrayValue("$.results").isNotEmpty();
	}

	private JsonContent<StarwarsApiList<StarwarsStarship>> readAndWriteJson() throws IOException {
		StarwarsApiList<StarwarsStarship> starships = readJson();
		return json.write(starships);
	}

	private StarwarsApiList<StarwarsStarship> readJson() throws IOException {
		return json.readObject(jsonSample());
	}

	private InputStream jsonSample() {
		return getClass().getResourceAsStream("/starships.json");
	}

}
