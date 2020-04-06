package com.example.booking;

import com.example.swapiclient.StarwarsApiClient;
import com.example.swapiclient.StarwarsStarship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StarShipServiceCachingTest {

	@Autowired
	private StarshipService starshipService;

	@Autowired
	private CacheManager cacheManager;

	@MockBean
	private StarwarsApiClient starwarsApiClient;

	@AfterEach
	void clearCache() {
		cacheManager.getCache("starship").clear();
	}

	@Test
	void cachesStarShip() {
		StarwarsStarship starwarsStarship = new StarwarsStarship();
		when(starwarsApiClient.findFirst(any())).thenReturn(Optional.of(starwarsStarship));

		assertThat(starshipService.findStarwarsStarship(new Starship("A-wing"))).isEqualTo(starwarsStarship);
		assertThat(starshipService.findStarwarsStarship(new Starship("A-wing"))).isEqualTo(starwarsStarship);
		verify(starwarsApiClient, times(1)).findFirst(any());
	}
}
