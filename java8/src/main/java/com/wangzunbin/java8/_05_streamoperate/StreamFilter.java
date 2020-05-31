package com.wangzunbin.java8._05_streamoperate;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * ClassName:StreamFilter  <br/>
 * Function: stream的过滤操作 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 17:40   <br/>
 */
public class StreamFilter {


    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 7, 7, 1);

        // 过滤 filter
        List<Integer> result = list.stream().filter(i -> i % 2 == 0).collect(toList());

        System.out.println(result);

        // 去重 distinct
        result = list.stream().distinct().collect(toList());

        System.out.println(result);

        // 跳过  skip
        result = list.stream().skip(50).collect(toList());

        System.out.println(result);

        // 限制多少个 limit
        result = list.stream().limit(50).collect(toList());

        System.out.println(result);


        list.forEach(System.out::println);

        list.forEach(System.out::println);
        list.forEach(System.out::println);

        for (int i : list) {
            System.out.println(i);
        }
    }
}
