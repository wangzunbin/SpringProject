package com.wangzunbin.java8._01_example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:Apple  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/5/31 10:18   <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apple {

    private String color;
    private Long weight;

}
