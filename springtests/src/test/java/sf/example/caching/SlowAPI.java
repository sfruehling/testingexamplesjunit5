package sf.example.caching;

public class SlowAPI {
    public String calculate(String isbn) {
        simulateSlowService();
        return "String"+isbn;
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
