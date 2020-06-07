package com.wangzunbin.java8._09_collectors;

import com.wangzunbin.java8._04_stream.Dish;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 * ClassName:CollectorsAction4  <br/>
 * Function: 源码API: ReferencePipeline <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/6/6 17:46   <br/>
 */
public class CollectorsAction4 {

    public static void main(String[] args) {
        testSummingDouble();
        testSummingLong();
        testSummingInt();
        testToCollection();
        testToConcurrentMap();
        testToConcurrentMapWithBinaryOperator();
        testToConcurrentMapWithBinaryOperatorAndSupplier();

        testToList();
        testToSet();

        testToMap();
        testToMapWithBinaryOperator();
        testToMapWithBinaryOperatorAndSupplier();
    }


    //  summingDouble和另一外形式求和, 一个返回结果是double类型(summingDouble), 一个返回结果是int类型
    private static void testSummingDouble() {
        System.out.println("testSummingDouble");
        Optional.of(CollectorsAction.menu.stream().collect(Collectors.summingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);

        Optional.of(CollectorsAction.menu.stream().map(Dish::getCalories).mapToInt(Integer::intValue).sum())
                .ifPresent(System.out::println);
    }

    // 求和, 返回结果是Long类型  summingLong
    private static void testSummingLong() {
        System.out.println("testSummingLong");
        Optional.of(CollectorsAction.menu.stream().collect(Collectors.summingLong(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    // 求和,  返回结果是int类型  summingInt
    private static void testSummingInt() {
        System.out.println("testSummingInt");
        Optional.of(CollectorsAction.menu.stream().collect(Collectors.summingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    // 过滤蔬菜的卡路里大于600, 收集成LinkedList类型数据
    private static void testToCollection() {
        System.out.println("testToCollection");
        Optional.of(CollectorsAction.menu.stream().filter(d -> d.getCalories() > 600).collect(Collectors.toCollection(LinkedList::new)))
                .ifPresent(System.out::println);
    }

    //  收集成一个以菜名为key, 菜的卡路里为value的map
    private static void testToConcurrentMap() {
        System.out.println("testToConcurrentMap");
        Optional.of(CollectorsAction.menu.stream()
                .collect(Collectors.toConcurrentMap(Dish::getName, Dish::getCalories)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }

    /**
     * Type:Total
     */
    //  以菜的类型分类为key, 以对应的数量总和为value
    private static void testToConcurrentMapWithBinaryOperator() {
        System.out.println("testToConcurrentMapWithBinaryOperator");
        Optional.of(CollectorsAction.menu.stream()
                .collect(Collectors.toConcurrentMap(Dish::getType, v -> 1L, (a, b) -> a + b)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }

    /**
     * Type:Total
     */
    //  转成并发map,  ConcurrentSkipListMap: 由于map的
//    并发场景下不能使用HashMap
//    JDK 1.7，在并发场景下使用HashMap会出现死循环，导致CPU使用率居高不下，而扩容是导致死循环的主要原因
//    JDK 1.8，虽然修复了HashMap扩容导致的死循环问题，但在高并发场景下，依然会有数据丢失和不准确的情况
//    为了保证Map容器的线程安全，Java实现了HashTable、ConcurrentHashMap、ConcurrentSkipListMap
//    HashTable、ConcurrentHashMap是基于HashMap实现的，适用于小数据量存取的场景
//            ConcurrentSkipListMap是基于TreeMap的设计原理实现的
//    ConcurrentSkipListMap是基于跳表实现的，而TreeMap是基于红黑树实现的
//    ConcurrentSkipListMap最大的特点是存取平均时间复杂度为O(log(n))，适用于大数据量存取的场景
    private static void testToConcurrentMapWithBinaryOperatorAndSupplier() {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();

        System.out.println("testToConcurrentMapWithBinaryOperatorAndSupplier");
        Optional.of(CollectorsAction.menu.stream()
                .collect(Collectors.toConcurrentMap(Dish::getType, v -> 1L, (a, b) -> a + b, ConcurrentSkipListMap::new)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }

    // 收集成list集合
    private static void testToList() {
        Optional.of(CollectorsAction.menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList()))
                .ifPresent(r -> {
                    System.out.println(r.getClass());
                    System.out.println(r);
                });
    }

    // 收集成hashset的集合
    private static void testToSet() {
        Optional.of(CollectorsAction.menu.stream().filter(Dish::isVegetarian).collect(Collectors.toSet()))
                .ifPresent(r -> {
                    System.out.println(r.getClass());
                    System.out.println(r);
                });
    }


    //  SynchronizedMap实现的是线程同步问题
    private static void testToMap() {
        System.out.println("testToMap");
        Optional.of(CollectorsAction.menu.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toMap(Dish::getName, Dish::getCalories),
                        Collections::synchronizedMap))
        )
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });

        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
            Thread key = entry.getKey();
            StackTraceElement[] value = entry.getValue();

            if (key.getId() != Thread.currentThread().getId())
                continue;
            System.out.println("==========" + key.getName());
            for (StackTraceElement ste : value) {
                if (ste.isNativeMethod())
                    continue;
                System.out.println(ste.getClassName());
                System.out.println("isNativeMethod>" + ste.isNativeMethod());
                System.out.println(ste.getMethodName());
                System.out.println(ste.getLineNumber());
                System.out.println(ste.getFileName());
            }
        }
    }

    /**
     * Type:Total
     * 收集的数据是以菜的类型为key, 数量总和为value
     */
    private static void testToMapWithBinaryOperator() {
        System.out.println("testToMapWithBinaryOperator");
        Optional.of(CollectorsAction.menu.stream()
                .collect(Collectors.toMap(Dish::getType, v -> 1L, (a, b) -> a + b)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }

    /**
     * Type:Total
     * 收集的数据变成Hashtable
     */
    private static void testToMapWithBinaryOperatorAndSupplier() {
        System.out.println("testToMapWithBinaryOperatorAndSupplier");
        Optional.of(CollectorsAction.menu.stream()
                .collect(Collectors.toMap(Dish::getType, v -> 1L, (a, b) -> a + b, Hashtable::new)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }
}
