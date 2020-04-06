package com.example.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StarshipBookingController.class)
@WithMockUser
class StarshipBookingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StarshipBookingService bookingService;

	@Test
	void callsServiceWithParameter_noResults() throws Exception {
		when(bookingService.findBookableStarships(200)).thenReturn(new ArrayList<>());

		mockMvc.perform(//
				get("/bookable-starships") //
						.header("capacity", "200") //
						.accept(APPLICATION_JSON) //
		) //
				.andExpect(status().is2xxSuccessful()) //
				.andExpect(jsonPath("$").isArray()) //
				.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	void callsServiceWithParameter_twoResults() throws Exception {
		List<Starship> twoStarships = Arrays.asList(new Starship(), new Starship());

		when(bookingService.findBookableStarships(42)).thenReturn(twoStarships);

		mockMvc.perform(//
				get("/bookable-starships") //
						.header("capacity", "42") //
						.accept(APPLICATION_JSON) //
		) //
				.andExpect(status().is2xxSuccessful()) //
				.andExpect(jsonPath("$").isArray()) //
				.andExpect(jsonPath("$").value(hasSize(2)));
	}

}
