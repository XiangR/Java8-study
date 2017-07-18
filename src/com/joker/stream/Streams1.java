package com.joker.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author xiangR
 *
 */
public class Streams1 {

	public static void main(String[] args) {

		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		stringCollection.add("11");
		stringCollection.add("12");

		/*
		 * filtering
		 * ��������"aaa2", "aaa1"
		 */
		stringCollection.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);

		/*
		 * filter ����� map ������ÿһ��Ԫ��
		 * 
		 * ��������AAA2 AAA1
		 */
		stringCollection.stream().filter((s) -> s.startsWith("a")).map(String::toUpperCase).forEach(System.out::println);

		/*
		 * sorted
		 * 
		 * ֧�ִ���ָ��˳��Ҳ��ʹ��Ĭ��˳��
		 * ��������AAA2 AAA1
		 */
		stringCollection.stream().filter((s) -> s.startsWith("a")).sorted((a, b) -> b.compareTo(a)).map(String::toUpperCase).forEach(System.out::println);

		/*
		 * mapping
		 * 
		 * ���������ĶԼ�����ı�������ͳһ�ĸ���
		 * ֧�ִ���ָ����ʽ
		 * 
		 */
		stringCollection.stream().map(String::toUpperCase).forEach(System.out::println);
		stringCollection.stream().map((s) -> s.toUpperCase()).forEach(System.out::println);

		/*
		 * matching
		 * ֧�ֿ���ɸѡ ���ҷ���Boolean
		 */
		boolean anyStartsWithA = stringCollection.stream().anyMatch((s) -> s.startsWith("a"));
		System.out.println(anyStartsWithA); // true

		boolean allStartsWithA = stringCollection.stream().allMatch((s) -> s.startsWith("a"));
		System.out.println(allStartsWithA); // false

		boolean noneStartsWithZ = stringCollection.stream().noneMatch((s) -> s.startsWith("q"));
		System.out.println(noneStartsWithZ); // true

		/*
		 * math
		 * �������е�����ɸѡ��int��
		 * count �õ�����
		 * sum �����ܺ�
		 */
		long startsWithA = stringCollection.stream().filter((s) -> s.startsWith("1")).mapToInt(i -> Integer.valueOf(i)).count();
		long startsWithB = stringCollection.stream().filter((s) -> s.startsWith("1")).mapToInt(i -> Integer.valueOf(i)).sum();

		System.out.println(startsWithA); // 3
		System.out.println(startsWithB); // 23

		/*
		 * reducing
		 * "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"
		 */
		Optional<String> reduce = stringCollection.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
		reduce.ifPresent(System.out::println);
	}
}
