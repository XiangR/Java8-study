package com.joker.stream;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Testing the order of execution.
 * 
 * @author xiangR
 * @date 2017年7月19日下午3:50:55
 *
 */
public class Streams5 {

	public static void main(String[] args) {
		List<String> strings = Arrays.asList("d2", "a2", "b1", "A", "b3", "c");

		test1(strings);
		test2(strings);
		test3(strings);
		test4(strings);
		test5(strings);
		test6(strings);
		test7(strings);
		test8(strings);
	}

	/*
	 * 通过Supplier来储存流
	 * 
	 */
	public static void test8(List<String> stringList) {
		Supplier<Stream<String>> streamSupplier = () -> stringList.stream().filter(s -> s.startsWith("a"));

		System.out.println(streamSupplier.get().anyMatch(s -> true));
		System.out.println(streamSupplier.get().noneMatch(s -> true));

		/*
		 * 这种形式会报 stream has already been operated upon or closed
		 * 
		 * 可以理解为supplier储存流只是储存到了 supplier中并没有去改变 stream的实现
		 * stream对象仍然只能读一次
		 * 但是可以使用supplier.get() 来得到同一个stream对象
		 * 
		 * Stream<String> stream = streamSupplier.get();
		 * System.out.println(stream.anyMatch(s -> true));
		 * System.out.println(stream.noneMatch(s -> true));
		 */
	}

	/*
	 * stream has already been operated upon or closed
	 * 流已经计算或已关闭
	 */
	public static void test7(List<String> stringList) {
		Stream<String> stream = stringList.stream().filter(s -> s.startsWith("a"));

		System.out.println(stream.anyMatch(s -> true));
		System.out.println(stream.noneMatch(s -> true));
	}

	/*
	 * 使用angMatch 类似于 || 从集合的第一个元素开始知道找到指定的元素即可
	 */
	public static void test6(List<String> stringList) {
		boolean anyMatch = stringList.stream().map(s -> {
			System.out.println("map:      " + s);
			return s.toUpperCase();
		}).anyMatch(s -> {
			System.out.println("anyMatch: " + s);
			return s.equals("A");
		});
		System.out.println(anyMatch);
	}

	/*
	 * 先filter 再 sorted 再mapper
	 */
	public static void test5(List<String> stringList) {
		stringList.stream().filter(s -> {
			System.out.println("filter:  " + s);
			return s.toLowerCase().startsWith("a");
		}).sorted((s1, s2) -> {
			System.out.printf("sort:    %s; %s\n", s1, s2);
			return s1.compareTo(s2);
		}).map(s -> {
			System.out.println("map:     " + s);
			return s.toUpperCase();
		}).forEach(s -> System.out.println("forEach: " + s));
	}

	// sorted = horizontal
	public static void test4(List<String> stringList) {
		stringList.stream().sorted((s1, s2) -> {
			System.out.printf("sort:    %s; %s\n", s1, s2);
			return s1.compareTo(s2);
		}).filter(s -> {
			System.out.println("filter:  " + s);
			return s.toLowerCase().startsWith("a");
		}).map(s -> {
			System.out.println("map:     " + s);
			return s.toUpperCase();
		}).forEach(s -> System.out.println("forEach: " + s));
	}

	/*
	 * 在使用filter之后使用map进行遍历处理处理完使用forEach进行遍历
	 */
	public static void test3(List<String> stringList) {
		stringList.stream().filter(s -> {
			System.out.println("filter:  " + s);
			return s.startsWith("a");
		}).map(s -> {
			System.out.println("map:     " + s);
			return s.toUpperCase();
		}).forEach(s -> System.out.println("forEach: " + s));
	}

	/*
	 * 在使用filter之前使用map进行遍历处理处理完使用forEach进行遍历
	 */
	public static void test2(List<String> stringList) {
		stringList.stream().map(s -> {
			System.out.println("map:     " + s);
			return s.toUpperCase();
		}).filter(s -> {
			System.out.println("filter:  " + s);
			return s.startsWith("B");
		}).forEach(s -> System.out.println("forEach: " + s));
	}

	/*
	 * 将Java 中的stream融合函数值编程
	 * 过去filter只使用了 filter(s -> s.equals(..)) or filter(s -> s)
	 * 
	 * 去看源代码 filter 传入的参数是 Predicate
	 * Predicate的 lambda表达式 为 return (t) -> test(t) && other.test(t);
	 * test(t) 返回类型为 Boolean 所以filter在使用lambda时 a -> { } 代码块的返回类型应为Boolean
	 * 
	 */
	public static void test1(List<String> stringList) {
		stringList.stream().filter(s -> {
			System.out.println("filter:  " + s);
			return true;
		}).forEach(s -> System.out.println("forEach: " + s));
	}

}