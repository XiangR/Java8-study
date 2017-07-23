package com.joker.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 
 * @author xiangR
 * @date 2017年7月20日上午10:19:43
 *
 */
public class Streams7 {

	static class Foo {
		String name;
		List<Bar> bars = new ArrayList<>();

		Foo(String name) {
			this.name = name;
		}
	}

	static class Bar {
		String name;

		Bar(String name) {
			this.name = name;
		}
	}

	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
	}

	/*
	 * test2 是 test1 的简单版本
	 * 通过使用peek来简化对Stream的操作
	 * 
	 * 同时理解了一点
	 * 对于 Stream 的mapToObj 即是在遍历中对对象执行操作
	 * mapToInt 则是对int型的操作
	 * mapToDouble 则是对double的操作
	 */
	static void test2() {
		IntStream.range(1, 4).mapToObj(num -> new Foo("Foo" + num))
				.peek(f -> IntStream.range(1, 4).mapToObj(num -> new Bar("Bar" + num + " <- " + f.name)).forEach(f.bars::add))
				.flatMap(f -> f.bars.stream()).forEach(b -> System.out.println(b.name));
	}

	static void test1() {
		List<Foo> foos = new ArrayList<>();
		IntStream.range(1, 4).forEach(num -> foos.add(new Foo("Foo" + num)));

		foos.forEach(f -> IntStream.range(1, 4).forEach(num -> f.bars.add(new Bar("Bar" + num + " <- " + f.name))));

		foos.stream().flatMap(f -> f.bars.stream()).forEach(b -> System.out.println(b.name));
	}

	/*
	 * peek 是个中间操作
	 * 它提供了一种对流中所有元素操作的方法，而不会把这个流消费掉（foreach 会把流消费掉）
	 * 
	 * stream 在遇到终结操作时才会执行操作 所以再没有collect 的时候并不会真的打印出内容
	 */
	static void test4() {
		Stream.of("one", "two", "three", "four").peek(e -> System.out.println("Peeked value: " + e)).map(String::toUpperCase)
				.peek(e -> System.out.println("Mapped value: " + e))
				.collect(Collectors.toList());
	}

	/*
	 * 理解stream的 map 与 flatMap
	 * faltMap 可以将嵌套的stream 转成同一个stream中进行操作
	 * 
	 */
	static void test3() {

		Supplier<Stream<String>> streamSupplier = () -> Stream.of(new String("your"), new String("world"));

		Stream<Stream<Character>> map = streamSupplier.get().map(w -> characterStream(w));
		map.forEach(s -> s.forEach(v -> System.out.println("map -> " + v)));

		Stream<Character> flatMap = streamSupplier.get().flatMap(w -> characterStream(w));
		flatMap.forEach(s -> System.out.println("flatMap -> " + s));
		System.out.println("end");
	}

	public static Stream<Character> characterStream(String s) {
		List<Character> result = new ArrayList<>();
		for (char c : s.toCharArray())
			result.add(c);
		return result.stream();
	}

}