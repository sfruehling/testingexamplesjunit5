package com.example.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StarshipBookingController {

	private final StarshipBookingService bookingService;

	@Autowired
	public StarshipBookingController(StarshipBookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping("/bookable-starships")
	public List<Starship> findStarship(@RequestHeader("capacity") String capacity) {
		return bookingService.findBookableStarships(Integer.parseInt(capacity));
	}

}
