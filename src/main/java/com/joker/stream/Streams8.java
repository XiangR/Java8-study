package com.joker.stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author xiangR
 * @date 2017年7月24日上午10:06:42
 * <p>
 * stream 中的一些组合操作
 * 稍微理解下 map mapToInt mapToObj的区别
 * mapToObj 即是在遍历中对 对象执行操作
 * mapToInt 则是对int型的操作
 * mapToDouble 则是对double的操作
 */
public class Streams8 {

    public static void main(String[] args) {
        Arrays.asList("a1", "a2", "a3").stream().findFirst().ifPresent(System.out::println);

        Stream.of("a1", "a2", "a3").map(s -> s.substring(1)).mapToInt(Integer::parseInt).max().ifPresent(System.out::println);

        IntStream.range(1, 4).mapToObj(i -> "a" + i).forEach(System.out::println);

        Arrays.stream(new int[]{1, 2, 3}).map(n -> 2 * n + 1).average().ifPresent(System.out::println);

        Stream.of(1.0, 2.0, 3.0).mapToInt(Double::intValue).mapToObj(i -> "a" + i).forEach(System.out::println);
    }
}
