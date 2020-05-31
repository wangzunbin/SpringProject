package com.wangzunbin.java8._02_lambda;

import com.wangzunbin.java8._01_example.Apple;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * ClassName:LambdaExpression  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 10:37   <br/>
 */


public class LambdaExpression {

    public static void main(String[] args) {
        Comparator<Apple> byColor = new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getColor().compareTo(o2.getColor());
            }
        };

        List<Apple> list = Collections.emptyList();
        list.sort(byColor);
        Comparator<Apple> byColor2 = (o1, o2) ->  o1.getColor().compareTo(o2.getColor());
        Comparator<Apple> byColor3 = Comparator.comparing(Apple::getColor);

        Function<String, Integer> flambda = s -> s.length();

        Predicate<Apple> p = (Apple a) -> a.getColor().equals("green");

        // 等价于上面的Predicate
        Function<Apple, Boolean> f = a -> a.getColor() .equals("green");

        Supplier<Apple> appleSupplier = Apple::new;
    }

    /**
     * 1. Predicate boolean test(T t) 断言
     * 2. Consumer accept(T t)
     * 3. Function<T, R>  R apply(T t)
     * 4. Supplier<T>   T get()
     *
     */
}
