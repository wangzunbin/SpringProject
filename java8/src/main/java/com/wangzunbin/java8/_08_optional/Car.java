package com.wangzunbin.java8._08_optional;

import lombok.Data;

import java.util.Optional;

/**
 * ClassName:Car  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 20:20   <br/>
 */

@Data
public class Car {

    private Optional<Insurance> insurance;

}
