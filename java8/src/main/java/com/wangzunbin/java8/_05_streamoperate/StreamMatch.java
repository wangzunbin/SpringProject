package com.wangzunbin.java8._05_streamoperate;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * ClassName:StreamMatch  <br/>
 * Function: match <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 17:51   <br/>
 */
public class StreamMatch {

    public static void main(String[] args) {

        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        // 所有都匹配这个条件
        boolean matched = stream.allMatch(i -> i > 10);
        System.out.println(matched);

        // 有一部分匹配这个条件
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        matched = stream.anyMatch(i -> i > 6);
        System.out.println(matched);

        // 都不匹配这个条件
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        matched = stream.noneMatch(i -> i < 0);
        System.out.println(matched);

    }
}
