package com.example.builders;

import com.example.swapiclient.StarwarsApiList;
import com.example.swapiclient.StarwarsStarship;

import java.util.Arrays;
import java.util.List;

public class StarwarsApiListFactory {

	public StarwarsApiList<StarwarsStarship> getAStarwarsStarship() {
		List<StarwarsStarship> starwarsStarship = Arrays.asList(new StarwarsStarship());
		StarwarsApiList<StarwarsStarship> starwarsApiList = new StarwarsApiList<>();
		starwarsApiList.setCount(1);
		starwarsApiList.setResults(starwarsStarship);
		return starwarsApiList;
	}
}
