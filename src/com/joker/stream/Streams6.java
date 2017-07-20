package com.joker.stream;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 
 * @author xiangR
 * @date 2017年7月20日上午1:18:45
 *
 */
public class Streams6 {

	public static void main(String[] args) throws IOException {
		test1();
		test2();
		test3();
		test4();
		test5();
	}

	private static void test5() {
		/*
		 * 理解 Stream.concat
		 */
		Stream<Integer> stream1 = IntStream.builder().add(1).add(3).add(5).add(7).add(9).build().boxed();
		Stream<Integer> stream2 = IntStream.builder().add(2).add(4).add(6).add(8).add(10).build().boxed();

		Stream.concat(stream1, stream2).sorted((a, b) -> a.compareTo(b)).forEach(k -> {
			System.out.println(String.format("concat - stream : %d", k));
		});
	}

	private static void test4() {
		/*
		 * 理解 Stream.of 底层实现 return Arrays.stream(values);
		 */
		Stream.of(new BigDecimal("1.2"), new BigDecimal("3.7")).mapToDouble(BigDecimal::doubleValue).average().ifPresent(System.out::println);
	}

	private static void test3() {
		IntStream.range(0, 10).average().ifPresent(System.out::println);
	}

	private static void test2() {

		/*
		 * 与 test1 得到同样的结果
		 */
		IntStream.builder().add(1).add(3).add(5).add(7).add(11).build().average().ifPresent(s -> {
			System.out.println(String.format("acerage: %f", s));
		});
	}

	/*
	 * 数组转化stream -> average
	 */
	private static void test1() {
		int[] ints = { 1, 3, 5, 7, 11 };
		Arrays.stream(ints).average().ifPresent(s -> {
			System.out.println(String.format("acerage: %f", s));
		});
	}
}
