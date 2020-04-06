package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "production")
@TestPropertySource(properties = { //
		"de.andrena.springbootadvancedtesting.username=testuser",
		"de.andrena.springbootadvancedtesting.password=testpasswd" })
class SecurityWithAuthTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void http4xxWithoutAuthentication() throws Exception {
		mockMvc.perform(get("/v2/api-docs")) //
				.andExpect(status().is4xxClientError());
	}

	@Test
	void http4xxWithWrongAuthentication() throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth("testuser", "WRONG");

		mockMvc.perform(get("/v2/api-docs").headers(httpHeaders)) //
				.andExpect(status().is4xxClientError());
	}

	@Test
	void http2xxWithRightAuthentication() throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth("testuser", "testpasswd");

		mockMvc.perform(get("/v2/api-docs").headers(httpHeaders)) //
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void http2xxWithRightAuthentication_withPostprocessor() throws Exception {
		mockMvc.perform(get("/v2/api-docs") //
				.with(SecurityMockMvcRequestPostProcessors.user("testuser"))) //
				.andExpect(status().is2xxSuccessful());
	}

}
