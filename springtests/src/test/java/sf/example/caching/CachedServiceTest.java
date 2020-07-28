package sf.example.caching;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = {"spring.cache.caffeine.spec=expireAfterAccess=2s"})
public class CachedServiceTest {
    @EnableCaching
    @TestConfiguration
    static class CacheTestConfiguration {

        @Bean
        public SlowAPI slowAPI() {
            return new SlowAPI();
        }

        @Bean
        public CachedService cachedService(SlowAPI slowApi) {
            return new CachedService(slowApi);
        }
    }

    @SpyBean
    private SlowAPI slowAPIspy;

    @Autowired
    public CachedService cachedService;

    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    @SuppressWarnings("ConstantConditions")
    void clearCache() {
        cacheManager.getCache(CachedService.CACHE_NAME_SLOW_API).clear();
    }

    @Test
    void callsBookApiOnce() {
        cachedService.calculateNumber("234");
        cachedService.calculateNumber("234");
        cachedService.calculateNumber("234");

        verify(slowAPIspy, times(1)).calculate("234");
    }

    @Test
    void willCache2Sec() throws InterruptedException {
        cachedService.calculateNumber("1");
        cachedService.calculateNumber("1");
        verify(slowAPIspy, times(1)).calculate("1");

        TimeUnit.SECONDS.sleep(2);

        cachedService.calculateNumber("1");
        verify(slowAPIspy, times(1)).calculate("1");
    }

    @Test
    void willReturnSameObjectOnCache() throws InterruptedException {
        Integer returnValue1 = cachedService.calculateNumber("1");
        Integer returnValue2 = cachedService.calculateNumber("1");
        verify(slowAPIspy, times(1)).calculate("1");

        assertThat(returnValue1).isEqualTo(returnValue2);
    }

    @Test
    void replace_willEvictCacheItem() {
        cachedService.calculateNumber("1");
        cachedService.calculateNumber("1");
        verify(slowAPIspy, times(1)).calculate("1");

        cachedService.replace("1");
        cachedService.calculateNumber("1");
        verify(slowAPIspy, times(2)).calculate("1");
    }
}
