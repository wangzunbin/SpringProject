package com.wzb.service.impl;

import com.wzb.service.IHelloService;
import org.springframework.stereotype.Service;

/**
 * ClassName:HelloServiceImpl  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 22:26   <br/>
 */

@Service
public class HelloServiceImpl implements IHelloService {

    @Override
    public String greeting(String name) {

        return "hello: " + name;
    }
}
