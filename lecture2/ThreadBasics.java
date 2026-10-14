import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadBasics {

    public static void main(String[] args) throws InterruptedException {
        Runnable a = new Worker("a");
        Runnable b = new Worker("b");
        Runnable c = new Worker("c");

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(a);
        executorService.execute(b);
        executorService.execute(c);

        executorService.shutdown();

        executorService.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("All workers finished!");
    }
}

class Worker implements Runnable {
    private final String name;

    public Worker(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(
                    name + ": " + i +
                            " [Thread: " + Thread.currentThread().getName() + "]");
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
    }

    
}