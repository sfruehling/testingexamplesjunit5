package com.example.booking;

import com.example.booking.StarshipBookingService;
import com.example.swapiclient.StarwarsApiClient;
import com.example.swapiclient.StarwarsStarship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = { "spring.cache.caffeine.spec=expireAfterAccess=2s" })
class StarshipBookingServiceCachingTest {

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
	void findBookableStarshipsCachesResponses() throws InterruptedException {
		when(starwarsApiClient.findAll(any())).thenReturn(Arrays.asList(new StarwarsStarship(), new StarwarsStarship()));

		assertThat(bookingService.findBookableStarships(0)).hasSize(2);
		assertThat(bookingService.findBookableStarships(0)).hasSize(2);
		verify(starwarsApiClient, times(1)).findAll(any());
	}

	@Test
	void findBookableStarshipsEntriesExpireAfterTwoSeconds() throws InterruptedException {
		when(starwarsApiClient.findAll(any())).thenReturn(Arrays.asList(new StarwarsStarship(), new StarwarsStarship()));

		assertThat(bookingService.findBookableStarships(0)).hasSize(2);
		assertThat(bookingService.findBookableStarships(0)).hasSize(2);
		verify(starwarsApiClient, times(1)).findAll(any());

		TimeUnit.SECONDS.sleep(2);

		when(starwarsApiClient.findAll(any())).thenReturn(Arrays.asList(new StarwarsStarship()));
		assertThat(bookingService.findBookableStarships(0)).hasSize(1);
		verify(starwarsApiClient, times(2)).findAll(any());
	}
}
