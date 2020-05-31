package com.wangzunbin.java8._08_optional;

/**
 * ClassName:NullPointerException  <br/>
 * Function: 空指针异常处理类 --> optional 需求<br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 20:21   <br/>
 */

public class NullPointerException {

    public static void main(String[] args) {
        // 需求:
        // 需求1: 原始写法, 就是要每个都要判断
//        String insuranceName = getInsuranceName(new Person());
//        System.out.println(insuranceName);

        // 需求2: 进阶写法就是
//        String result = getInsuranceNameByDeepDoubts(new Person());
//        System.out.println(result);

    }
//
//    private static String getInsuranceNameByDeepDoubts(Person person) {
//        if (null != person) {
//            Car car = person.getCar();
//            if (null != car) {
//                Insurance insurance = car.getInsurance();
//                if (null != insurance) {
//                    return insurance.getName();
//                }
//            }
//        }
//
//        return "UNKNOWN";
//    }
//
//    // 需求3：第二种写法
//    private static String getInsuranceNameByMulExit(Person person) {
//        final String defaultValue = "UNKNOWN";
//
//        if (null == person)
//            return defaultValue;
//        Car car = person.getCar();
//        if (null == car)
//            return defaultValue;
//
//        Insurance insurance = car.getInsurance();
//        if (null == insurance)
//            return defaultValue;
//
//        return insurance.getName();
//    }
//
//    // 下面的弊端是, person有可能是空的, car也有可能是空, insurance也可能是空的
//    private static String getInsuranceName(Person person) {
//        return person.getCar().getInsurance().getName();
//    }
}
