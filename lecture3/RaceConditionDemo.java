import java.util.concurrent.*;
public class RaceConditionDemo {

    static class Counter {
        static int count = 0;

        public synchronized void increment() {
            int oldCount = count;

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            count = oldCount + 1;
        }

        public int getCount() {
            return count;
        }
        
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 200; i++) {
            executor.execute(() -> counter.increment());
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Expected: 200");
        System.out.println("Actual: " + counter.getCount());
    }
}
