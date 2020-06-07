package com.wangzunbin.java8._09_collectors;

import com.wangzunbin.java8._04_stream.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * ClassName:CollectorsAction3  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/6/6 17:22   <br/>
 */
public class CollectorsAction3 {

    // 分析:可以看到reduce方法接受一个函数，这个函数有两个参数，第一个参数是上次函数执行的返回值（也称为中间结果），
    //第二个参数是stream中的元素，这个函数把这两个值相加，得到的和会被赋值给下次执行这个函数的第一个参数。
    //要注意的是：第一次执行的时候第一个参数的值是Stream的第一个元素，第二个参数是Stream的第二个元素。

    public static void main(String[] args) {

        testPartitioningByWithPredicate();
        testPartitioningByWithPredicateAndCollector();
        testReducingBinaryOperator();
        testReducingBinaryOperatorAndIdentiy();
        testReducingBinaryOperatorAndIdentiyAndFunction();
        testSummarizingDouble();
        testSummarizingLong();
        testSummarizingInt();

    }

    // 根据是否是水果分组  partitioningBy
    private static void testPartitioningByWithPredicate() {
        System.out.println("testPartitioningByWithPredicate");
        Map<Boolean, List<Dish>> collect = CollectorsAction.menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        Optional.of(collect).ifPresent(System.out::println);

    }

    // 先对是否是水果分组, 并对组内的卡路里的平均值求和   averagingInt
    private static void testPartitioningByWithPredicateAndCollector() {
        System.out.println("testPartitioningByWithPredicateAndCollector");
        Map<Boolean, Double> collect = CollectorsAction.menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.averagingInt(Dish::getCalories)));
        Optional.of(collect).ifPresent(System.out::println);
    }

    //  把里面卡路里最大的数据拿出来  reducing --> 拿出前面比较最大的值来进行下个逻辑 maxBy
    private static void testReducingBinaryOperator() {
        System.out.println("testReducingBinaryOperator");
        CollectorsAction.menu.stream().collect(
                Collectors.reducing(
                        BinaryOperator.maxBy(
                                Comparator.comparingInt(Dish::getCalories)
                        )
                )
        ).ifPresent(System.out::println);
    }

    //  求和的第一种写法: 把那次前面相加的值求和    -->  求和   reducing
    private static void testReducingBinaryOperatorAndIdentiy() {
        System.out.println("testReducingBinaryOperatorAndIdentiy");
        Integer result = CollectorsAction.menu.stream()
                .map(Dish::getCalories).collect(Collectors.reducing(0, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }

    // 求和的第二种写法  reducing
    private static void testReducingBinaryOperatorAndIdentiyAndFunction() {
        System.out.println("testReducingBinaryOperatorAndIdentiyAndFunction");
        Integer result = CollectorsAction.menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }

    // summarizingDouble -->  求出double类型的 {count=9, sum=4200.000000, min=120.000000, average=466.666667, max=800.000000}
    private static void testSummarizingDouble() {
        System.out.println("testSummarizingDouble");
        Optional.of(CollectorsAction.menu.stream().collect(Collectors.summarizingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    // 求出long整型类型的数据出来  --->   {count=9, sum=4200, min=120, average=466.666667, max=800}
    private static void testSummarizingLong() {
        System.out.println("testSummarizingLong");
        Optional.of(CollectorsAction.menu.stream().collect(Collectors.summarizingLong(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    //  求出int类型的数据出来  -->  {count=9, sum=4200, min=120, average=466.666667, max=800}
    private static void testSummarizingInt() {
        System.out.println("testSummarizingLong");
        Optional.of(CollectorsAction.menu.stream().collect(Collectors.summarizingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }
}
