package com.joker.stream;

import java.util.Arrays;

/**
 * 
 * @author xiangR
 * @date 2017年7月31日下午5:30:01
 *
 *       stream 的新特性的组合使用
 */
public class Streams9 {

	public static void main(String[] args) {
		Arrays.asList("a1", "a2", "b1", "c2", "c1").stream().
				filter(s -> s.startsWith("c")).
				map(String::toUpperCase).
				sorted((k, v) -> v.compareTo(k)).
				forEach(System.out::println);

		// C2
		// C1
	}
}
