package sf.example.spring.caching;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public class CachedService {
    public static final String CACHE_NAME_SLOW_API = "slowApi";
    private SlowAPI slowApi;

    public CachedService(SlowAPI slowApi) {
        this.slowApi = slowApi;
    }

    @Cacheable(CACHE_NAME_SLOW_API)
    public String calculateNumber(String isbn) {
        return slowApi.calculate(isbn);
    }

    @SuppressWarnings("unused")
    @CacheEvict(CACHE_NAME_SLOW_API)
    public void replace(String isbn){
    }
}
