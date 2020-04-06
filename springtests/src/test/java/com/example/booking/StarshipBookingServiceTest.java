package com.example.booking;

import com.example.swapiclient.StarwarsApiClient;
import com.example.swapiclient.StarwarsStarship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class StarshipBookingServiceTest {

	@Autowired
	private StarshipBookingService bookingService;

	@Autowired
	private CacheManager cacheManager;

	@MockBean
	private StarwarsApiClient starwarsApiClient;

	@AfterEach
	void clearCache() {
		cacheManager.getCache("bookable-starships").clear();
	}

	@Test
	void returnsStarshipsFromStarwarsApiClient() {
		List<StarwarsStarship> twoStarships = Arrays.asList( //
				new StarwarsStarship("A-wing"), //
				new StarwarsStarship("Jedi starfighter"));

		when(starwarsApiClient.findAll(any())).thenReturn(twoStarships);

		List<Starship> result = bookingService.findBookableStarships(0);

		assertThat(result).hasSize(2) //
				.extracting(Starship::getName).containsExactly("A-wing", "Jedi starfighter");

		verify(starwarsApiClient).findAll(any());
	}

	@Test
	void cachesStarshipsFromStarwarsApiClient() {
		when(starwarsApiClient.findAll(any())).thenReturn(Arrays.asList(new StarwarsStarship(), new StarwarsStarship()));

		assertThat(bookingService.findBookableStarships(0)).hasSize(2);
		assertThat(bookingService.findBookableStarships(0)).hasSize(2);
		assertThat(bookingService.findBookableStarships(0)).hasSize(2);

		verify(starwarsApiClient, times(1)).findAll(any());
	}

}
