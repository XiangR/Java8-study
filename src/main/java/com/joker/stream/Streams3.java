package com.joker.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xiangR
 * @date 2017年7月19日下午1:28:38
 */
public class Streams3 {

    public static final int MAX = 1000000;

    public static void main(String[] args) {
        /*
		 * 使用parallel的是得到并行的stream可以更大的提升效率
		 * 但是其不具有线程传播性
		 */
        sortSequential(); // sequential sort took: 1534 ms
        System.out.println("\n--------------\n");
        sortParallel(); // parallel sort took: 637 ms

        printSequential();
        System.out.println("\n--------------\n");
        printParallel();
    }

    public static void printSequential() {
        List<Integer> numbers = getIntegerList(100);
        numbers.stream().map(s -> "Sequential - " + s).forEach(System.out::println);
        int sum = numbers.parallelStream().mapToInt(a -> a).sum();
        System.out.println(sum); // sum -> 4950
    }

    public static void printParallel() {
        List<Integer> numbers = getIntegerList(100);
        numbers.parallelStream().map(s -> "Parallel - " + s).forEach(System.out::println);
        int sum = numbers.parallelStream().mapToInt(a -> a).sum();
        System.out.println(sum); // sum -> 4950
    }

    public static void sortSequential() {
        List<String> values = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        // sequential

        long t0 = System.nanoTime();

        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
    }

    public static void sortParallel() {
        List<String> values = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        // parallel
        long t0 = System.nanoTime();

        long count = values.parallelStream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    }

    private static List<Integer> getIntegerList(Integer count) {
        count = count == null || count == 0 ? 50 : count;

        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < count; i++) {
            numbers.add(i);
        }
        return numbers;
    }
}
