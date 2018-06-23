package cz.filipklimes.edu.datastructure;

import java.util.*;

class HeapBenchmark
{

    public static void main(String[] args)
    {
        long[] binaryMinHeapPushTimes = new long[10];
        long[] binaryMinHeapPopTimes = new long[10];
        long[] queueAddTimes = new long[10];
        long[] queuePollTimes = new long[10];

        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int[] numbers = new int[10_000_000];
            for (int j = 0; j < numbers.length; j++) {
                numbers[j] = random.nextInt();
            }

            BinaryMinHeap binaryMinHeap = new BinaryMinHeap();
            Queue<Integer> integerQueue = new PriorityQueue<>();

            long start = System.nanoTime();
            for (int number : numbers) {
                binaryMinHeap.push(number);
            }
            binaryMinHeapPushTimes[i] = System.nanoTime() - start;
            start = System.nanoTime();
            for (int ignored : numbers) {
                binaryMinHeap.pop();
            }
            binaryMinHeapPopTimes[i] = System.nanoTime() - start;

            start = System.nanoTime();
            for (int number : numbers) {
                integerQueue.add(number);
            }
            queueAddTimes[i] = System.nanoTime() - start;
            start = System.nanoTime();
            for (int ignored : numbers) {
                integerQueue.poll();
            }
            queuePollTimes[i] = System.nanoTime() - start;
            System.out.printf("Iteration %d done..%n", i);
        }

        System.out.printf("BinaryMinHeap#push: %3fms +/- %3fms%n", average(binaryMinHeapPushTimes) / 1_000_000, deviation(binaryMinHeapPushTimes) / 1_000_000);
        System.out.printf("PriorityQueue<Integer>#add: %3fms +/- %3fms%n", average(queueAddTimes) / 1_000_000, deviation(queueAddTimes) / 1_000_000);

        System.out.printf("BinaryMinHeap#pop: %3fms +/- %3fms%n", average(binaryMinHeapPopTimes) / 1_000_000, deviation(binaryMinHeapPopTimes) / 1_000_000);
        System.out.printf("PriorityQueue<Integer>#poll: %3fms +/- %3fms%n", average(queuePollTimes) / 1_000_000, deviation(queuePollTimes) / 1_000_000);
    }

    private static double average(long[] values)
    {
        long total = 0;
        for (long value : values) {
            total += value;
        }
        return total / values.length;
    }

    private static double deviation(long[] values)
    {
        double mean = average(values);
        long sum = 0;
        for (long value : values) {
            sum += (value - mean) * (value - mean);
        }

        return Math.sqrt(sum / values.length);
    }

}
