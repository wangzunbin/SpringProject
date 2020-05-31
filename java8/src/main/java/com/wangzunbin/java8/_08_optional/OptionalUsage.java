package com.wangzunbin.java8._08_optional;

import java.util.Optional;

/**
 * ClassName:OptionalUsage  <br/>
 * Function:  NullPointerException是需求, 这个是解决方案 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 20:28   <br/>
 */
public class OptionalUsage {

    public static void main(String[] args) {
        /************  Optional的创建 start  ************/
        // 1. 第一种
        Optional<Insurance> optional1 = Optional.empty();
//        optional1.get();
        // 2. 第二种
        Optional<Insurance> optional2 = Optional.of(new Insurance());
//        optional2.get();

        // 3. 第三种对上面的综合使用
        Optional<Insurance> optional3 = Optional.ofNullable(null);
//        // 为null的话就返回入参
//        optional3.orElseGet(Insurance::new);
//        // 为null的话就返回入参的值
//        optional3.orElse(new Insurance());
//        optional3.orElseThrow(RuntimeException::new);
//        try {
//            optional3.orElseThrow(() -> new BusinessException("不能为空"));
//        } catch (BusinessException e) {
//            e.printStackTrace();
//        }
//        Optional<Insurance> insuranceOptional1 = Optional.ofNullable(new Insurance());
        Optional<Insurance> insuranceOptional1 = Optional.ofNullable(new Insurance("王尊斌"));

        // 多层庞孔
//        Insurance insurance = insuranceOptional1.filter(i -> i.getName() != null).get();
//        System.out.println(insurance);

        // 转成name来判空
        Optional<String> nameOptional = insuranceOptional1.map(i -> i.getName());
        // 这个对象里面的name有值就要输出自己, 否则输出empty value
        System.out.println(nameOptional.orElse("empty Value"));

        // 判断是否为空
        System.out.println(nameOptional.isPresent());

        // 有值, 就输出消费
        nameOptional.ifPresent(System.out::println);


//        System.out.println(getInsuranceName(null));
//        System.out.println(getInsuranceNameByOptional(null));

    }

    private static String getInsuranceName(Insurance insurance) {
        if (null == insurance)
            return "unknown";
        return insurance.getName();
    }

    private static String getInsuranceNameByOptional(Insurance insurance) {
        return Optional.ofNullable(insurance).map(Insurance::getName).orElse("unknown");
    }
}
