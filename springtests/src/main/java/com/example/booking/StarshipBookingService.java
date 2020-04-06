package com.example.booking;

import com.example.swapiclient.StarwarsApiClient;
import com.example.swapiclient.StarwarsStarship;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StarshipBookingService {

	private StarwarsApiClient starwarsApiClient;

	public StarshipBookingService(StarwarsApiClient starwarsApiClient) {
		this.starwarsApiClient = starwarsApiClient;
	}

	@Cacheable("bookable-starships")
	public List<Starship> findBookableStarships(int minimalPassengerCapacity) {
		List<StarwarsStarship> starwarsStarships = starwarsApiClient
				.findAll(starship -> starship.canHoldPassengers(minimalPassengerCapacity));

		return starwarsStarships.stream().map(Starship::new).collect(Collectors.toList());
	}

}
