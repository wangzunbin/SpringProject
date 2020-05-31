package com.wangzunbin.java8._08_optional;

import java.util.Optional;

/**
 * ClassName:OptionalInAction  <br/>
 * Function: optional综合实战 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 21:05   <br/>
 */

public class OptionalInAction {

    public static void main(String[] args) {
        Optional.of(getInsuranceNameByOptional(null)).ifPresent(System.out::println);
//        Car car = new Person().getCar().get();
    }

    private static String getInsuranceNameByOptional(Person person){
        // 错误的展示
//        Optional.ofNullable(person)
//                .map(Person::getCar)
//                .map(Car::getInsurance)
//                .map(Insurance::getName);
//        return "";
        // 高级写法
        return Optional.ofNullable(person).flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknown");
    }
}
