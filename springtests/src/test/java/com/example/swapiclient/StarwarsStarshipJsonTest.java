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
class StarwarsStarshipJsonTest {

	@Autowired
	private JacksonTester<StarwarsStarship> json;

	@Test
	void hasName() throws IOException {
		JsonContent<StarwarsStarship> starshipJson = readAndWriteJson();

		assertThat(starshipJson).extractingJsonPathStringValue("$.name").isNotBlank();
	}

	@Test
	void serialization() throws IOException {
		JsonContent<StarwarsStarship> starshipJson = readAndWriteJson();

		assertThat(starshipJson).isEqualToJson(jsonSample());
		assertThat(starshipJson).isStrictlyEqualToJson(jsonSample());
	}

	private JsonContent<StarwarsStarship> readAndWriteJson() throws IOException {
		StarwarsStarship starship = json.readObject(jsonSample());
		return json.write(starship);
	}

	private InputStream jsonSample() {
		return getClass().getResourceAsStream("/starship.json");
	}

}
