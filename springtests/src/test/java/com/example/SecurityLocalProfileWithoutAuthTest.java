package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "local")
class SecurityLocalProfileWithoutAuthTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void noAuthRequired() throws Exception {
		mockMvc.perform(get("/v2/api-docs")) //
				.andExpect(status().is2xxSuccessful());
	}

}