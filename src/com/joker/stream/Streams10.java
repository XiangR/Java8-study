package com.joker.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 
 * @author xiangR
 * @date 2017年7月31日下午5:31:32
 *
 *       理解 Collectors 的属性
 */
public class Streams10 {

	static class Person {
		String name;
		int age;

		Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static void main(String[] args) {
		List<Person> persons = Arrays.asList(new Person("Max", 18), new Person("Peter", 23), new Person("Pamela", 23), new Person("David", 12));

		// test1(persons);
		// test2(persons);
		// test3(persons);
		// test4(persons);
		// test5(persons);
		// test6(persons);
		// test7(persons);
		test8(persons);
		// test9(persons);
	}

	public static void test1(List<Person> persons) {
		List<Person> filtered = persons.stream().filter(p -> p.name.startsWith("P")).collect(Collectors.toList());

		System.out.println(filtered); // [Peter, Pamela]
	}

	public static void test2(List<Person> persons) {

		/*
		 *
		 * Collectors.groupingBy(p -> p.age)
		 * 使用集合中的一个元素进行分组
		 * 
		 * result：
		 * age 18: [Max]
		 * age 23: [Peter, Pamela]
		 * age 12: [David]
		 * 
		 */
		Map<Integer, List<Person>> personsByAge = persons.stream().collect(Collectors.groupingBy(p -> p.age));
		personsByAge.forEach((age, p) -> System.out.format("age %s: %s\n", age, p));

	}

	public static void test3(List<Person> persons) {

		/*
		 * 取集合对象中某一个属性进行操作
		 * 
		 * result:
		 * averageCollect: 19.0; averageMap: 19.0
		 * sumCollect: 76; sumMap: 76
		 */
		Double averageAge1 = persons.stream().collect(Collectors.averagingInt(p -> p.age));
		Double averageAge2 = persons.stream().mapToDouble(a -> a.age).average().getAsDouble();

		Integer sumAge1 = persons.stream().collect(Collectors.summingInt((p -> p.age)));
		Integer sumAge2 = persons.stream().mapToInt(a -> a.age).sum();

		System.out.format("averageCollect: %s; averageMap: %s", averageAge1, averageAge2);
		System.out.println();
		System.out.format("sumCollect: %s; sumMap: %s", sumAge1, sumAge2);
	}

	public static void test4(List<Person> persons) {

		/*
		 * IntSummaryStatistics{count=4, sum=76, min=12, average=19,000000,
		 * max=23}
		 */
		IntSummaryStatistics ageSummary = persons.stream().collect(Collectors.summarizingInt(p -> p.age));

		System.out.println(ageSummary);
	}

	public static void test5(List<Person> persons) {
		String names = persons.stream().filter(p -> p.age >= 18).map(p -> p.name).collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

		System.out.println(names);
		// In Germany Max and Peter and Pamela are of legal age.
	}

	public static void test6(List<Person> persons) {
		Map<Integer, String> map = persons.stream().collect(Collectors.toMap(p -> p.age, p -> p.name, (name1, name2) -> name1 + ";" + name2));

		System.out.println(map);
		// {18=Max, 23=Peter;Pamela, 12=David}
	}

	public static void test7(List<Person> persons) {
		Collector<Person, StringJoiner, String> personNameCollector = Collector.of(() -> new StringJoiner(" | "), // supplier
				(j, p) -> j.add(p.name.toUpperCase()), // accumulator
				(j1, j2) -> j1.merge(j2), // combiner
				StringJoiner::toString); // finisher

		String names = persons.stream().collect(personNameCollector);

		System.out.println(names); // MAX | PETER | PAMELA | DAVID
	}

	public static void test8(List<Person> persons) {
		Collector<Person, StringJoiner, String> personNameCollector = Collector.of(() -> {
			System.out.println("supplier");
			return new StringJoiner(" | ");
		}, (j, p) -> {
			System.out.format("accumulator: p=%s; j=%s\n", p, j);
			j.add(p.name.toUpperCase());
		}, (j1, j2) -> {
			System.out.println("merge");
			return j1.merge(j2);
		}, j -> {
			System.out.println("finisher");
			return j.toString();
		});

		String names = persons.stream().collect(personNameCollector);

		System.out.println(names); // MAX | PETER | PAMELA | DAVID
	}

	public static void test9(List<Person> persons) {
		Collector<Person, StringJoiner, String> personNameCollector = Collector.of(() -> {
			System.out.println("supplier");
			return new StringJoiner(" | ");
		}, (j, p) -> {
			System.out.format("accumulator: p=%s; j=%s\n", p, j);
			j.add(p.name.toUpperCase());
		}, (j1, j2) -> {
			System.out.println("merge");
			return j1.merge(j2);
		}, j -> {
			System.out.println("finisher");
			return j.toString();
		});

		String names = persons.parallelStream().collect(personNameCollector);

		System.out.println(names); // MAX | PETER | PAMELA | DAVID
	}
}
