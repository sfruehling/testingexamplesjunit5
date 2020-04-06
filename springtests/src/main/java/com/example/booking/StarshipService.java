package com.example.booking;

import com.example.swapiclient.StarwarsApiClient;
import com.example.swapiclient.StarwarsStarship;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StarshipService {
	private StarwarsApiClient starwarsApiClient;

	public StarshipService(StarwarsApiClient starwarsApiClient) {
		this.starwarsApiClient = starwarsApiClient;
	}

	@Cacheable("starship")
	public StarwarsStarship findStarwarsStarship(Starship starshipToRetrieve) {
		return starwarsApiClient
				.findFirst(starship -> starship.getName().equalsIgnoreCase(starshipToRetrieve.getName()))
				.orElseThrow(() -> new NoSuchElementException("Starship not found"));
	}
}
