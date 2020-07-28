package sf.example.mockmvc;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarshipBookingService {

    @SuppressWarnings("unused")
    public List<Starship> findBookableStarships(int capacity) {
        return null;
    }

}
