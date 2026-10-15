import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSum {
    private static int THRESHOLD = 20_000;

    static class SumTask extends RecursiveTask<Long> {
        private final int[] numbers;
        private final int start;
        private final int end;

        SumTask(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            // small enough (base case)
            if (end - start < THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += numbers[i];
                }
                return sum;
            }

            // not small enough: divide and conquer
            int mid = (start + end) / 2;

            SumTask left = new SumTask(numbers, start, mid);
            SumTask right = new SumTask(numbers, mid, end);

            left.fork();

            long rightSum = right.compute();

            long leftSum = left.join();

            return leftSum + rightSum;
        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[100_000_000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        ForkJoinPool pool = new ForkJoinPool();

        int[] thresholds = {
                100,
                1_000,
                5_000,
                20_000,
                100_000,
                1_000_000,
                10_000_000,
                100_000_000
        };
        long expected = (long) numbers.length * (numbers.length + 1) / 2;

        for (int threshold : thresholds) {

            THRESHOLD = threshold;

            // Warm up the JVM before measuring
            for (int i = 0; i < 3; i++) {
                pool.invoke(new SumTask(numbers, 0, numbers.length));
            }

            double totalTime = 0;

            // Run 5 experiments for each threshold
            for (int i = 0; i < 5; i++) {

                long start = System.nanoTime();

                long result = pool.invoke(
                        new SumTask(numbers, 0, numbers.length));

                long end = System.nanoTime();

                if (result != expected) {
                    throw new IllegalStateException("Incorrect sum!");
                }

                totalTime += (end - start) / 1_000_000.0;
            }

            System.out.printf(
                    "Threshold: %,9d | Average: %.3f ms%n",
                    threshold,
                    totalTime / 5);
        }

        pool.shutdown();
    }
}
