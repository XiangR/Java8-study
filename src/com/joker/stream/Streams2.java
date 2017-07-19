package com.joker.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author xiangR
 * @date : 2017年7月19日 上午10:27:45
 */
public class Streams2 {

	public static void main(String[] args) {

		List<String> stringList = new ArrayList<>();
		stringList.add("ddd2");
		stringList.add("aaa2");
		stringList.add("bbb1");
		stringList.add("aaa1");
		stringList.add("bbb3");
		stringList.add("ccc");
		stringList.add("bbb2");
		stringList.add("ddd1");

		/*
		 * sorted
		 * 实现排序，可传入排序方式
		 */
		stringList.stream().sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);

		/*
		 * 排序并不会对原来的集合产生影响
		 * 利用stream 中的collect将转化的集合赋值给一个新的对象
		 */
		List<String> stringListAfter = stringList.stream().sorted((a, b) -> b.compareTo(a)).collect(Collectors.toList());

		System.out.println("sort prior" + stringList);
		System.out.println("sort agter" + stringListAfter);

	}

}
