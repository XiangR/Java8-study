package com.joker.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author xiangR
 * @date 2017年7月19日下午1:39:35
 */
public class Streams4 {

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                System.out.println(i);
            }
        }

        IntStream.range(0, 10).forEach(i -> {
            if (i % 2 == 1)
                System.out.println(i);
        });

        IntStream.range(0, 10).filter(i -> i % 2 == 1).forEach(System.out::println);

        OptionalInt reduced1 = IntStream.range(0, 10).reduce((a, b) -> a + b);
        System.out.println(reduced1.getAsInt());

        int reduced2 = IntStream.range(0, 10).reduce(7, (a, b) -> a + b);
        System.out.println(reduced2);

        int sum1 = getIntegerList(100).parallelStream().mapToInt(a -> a).sum();
        int sum2 = getIntegerList2(100).parallelStream().mapToInt(a -> a).sum();
        System.out.println(String.format("sum1: %d; sum2: %d", sum1, sum2));

        rangeDistinguish();
    }

    /**
     * 构造一个集合
     *
     * @param count
     * @return
     */
    private static List<Integer> getIntegerList(Integer count) {
        count = count == null || count == 0 ? 50 : count;

        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < count; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    /**
     * 使用stream的形式来构造一个集合
     *
     * @param count
     * @return
     */
    private static List<Integer> getIntegerList2(Integer count) {
        count = count == null || count == 0 ? 50 : count;

        List<Integer> collect = IntStream.range(0, count).boxed().collect(Collectors.toList());
        return collect;
    }

    /*
     * 理解range rangeClosed
     */
    public static void rangeDistinguish() {

		/*
         * range 的使用类似于一个for循环的遍历但是不包含最后一个数
		 * 可理解为 i = 0; i < count; i ++
		 */
        Supplier<Stream<Integer>> supplierRange = () -> IntStream.range(0, 10).boxed();
        supplierRange.get().forEach(k -> {
            System.out.println(String.format("range: %d", k));
        });
        System.out.println(String.format("count -> range: %d", supplierRange.get().count()));
		
		/*
		 * rangeClosed 的使用
		 * 可理解为 i = 0; i <= count; i ++
		 */
        Supplier<Stream<Integer>> supplierRangeClosed = () -> IntStream.rangeClosed(0, 10).boxed();
        supplierRangeClosed.get().forEach(k -> {
            System.out.println(String.format("rangeClosed: %d", k));
        });
        System.out.println(String.format("count -> rangeClosed: %d", supplierRangeClosed.get().count()));
    }
}
