package sf.example.caching;

public class SlowAPI {
    public int calculate(String isbn) {
        simulateSlowService();
        return 42;
    }

    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
