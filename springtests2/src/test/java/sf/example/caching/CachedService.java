package sf.example.caching;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public class CachedService {
    public static final String CACHE_NAME_SLOW_API = "slowApi";
    private SlowAPI slowApi;

    public CachedService(SlowAPI slowApi) {
        this.slowApi = slowApi;
    }

    @Cacheable(CACHE_NAME_SLOW_API)
    public int calculateNumber(String isbn) {
        return slowApi.calculate(isbn);
    }

    @CacheEvict(CACHE_NAME_SLOW_API)
    public void replace(String isbn){

    }
}
