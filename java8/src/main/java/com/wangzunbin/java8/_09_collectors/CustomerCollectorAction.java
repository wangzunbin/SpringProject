package com.wangzunbin.java8._09_collectors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

/**
 * ClassName:CustomerCollectorAction  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/6/7 17:59   <br/>
 */

public class CustomerCollectorAction {

    public static void main(String[] args) {
        Collector<String, List<String>, List<String>> collector = new ToListCollector<>();

        String[] arrs = new String[]{"Alex", "Wang", "Hello", "Lambda", "Collector", "Java 8", "Stream"};

        List<String> result = Arrays.asList("Alex", "Wang", "Hello", "Lambda", "Collector", "Java 8", "Stream")
                .parallelStream()
                .filter(s -> s.length() >= 5)
                .collect(collector);

        System.out.println(result);

    }
}
