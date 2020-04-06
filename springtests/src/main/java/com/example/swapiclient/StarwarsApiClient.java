package com.example.swapiclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StarwarsApiClient {

	private final RestTemplate restTemplate;

	@Autowired
	public StarwarsApiClient(RestTemplateBuilder restTemplateBuilder) {
		this(restTemplateBuilder.build());
	}

	StarwarsApiClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Optional<StarwarsStarship> findFirst(Predicate<StarwarsStarship> predicate) {
		ResponseEntity<StarwarsApiList<StarwarsStarship>> result = restTemplate.exchange("/starships", HttpMethod.GET,
				HttpEntity.EMPTY, new ParameterizedTypeReference<StarwarsApiList<StarwarsStarship>>() {
				});
		return result.getBody().getResults().stream().filter(predicate).findFirst();
	}

	public List<StarwarsStarship> findAll(Predicate<StarwarsStarship> predicate) {
		return new ArrayList<>();
	}
}
