package com.wangzunbin.java8._08_optional;

import lombok.Data;

import java.util.Optional;

/**
 * ClassName:Person  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 20:19   <br/>
 */

@Data
public class Person {

//    private Car car;
    private Optional<Car> car;

}
