package com.wangzunbin.java8._05_streamoperate;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * ClassName:StreamReduce  <br/>
 * Function: reduce操作: 操作可以实现从一组值中生成一个值。它有三个override的方法，我们一起来看看具体的实现。 <br/>
 *  中断操作
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 18:16   <br/>
 */
public class StreamReduce {

    public static void main(String[] args) {

        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        // 求和
        Integer result = stream.reduce(0, Integer::sum);
        System.out.println(result);

        // 求和
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream.reduce((i, j) -> i + j).ifPresent(System.out::println);

        // 求最大值
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream.reduce(Integer::max).ifPresent(System.out::println);

        // 求最小值
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream.reduce(Integer::min).ifPresent(System.out::println);

        // 求最小值
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream.reduce((i, j) -> i > j ? j : i).ifPresent(System.out::println);

        // 把偶数过滤出来, 接着把它们相乘
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        int result2 = stream.filter(i -> i % 2 == 0).reduce(1, (i, j) -> i * j);
        Optional.of(result2).ifPresent(System.out::println);

    }
}
