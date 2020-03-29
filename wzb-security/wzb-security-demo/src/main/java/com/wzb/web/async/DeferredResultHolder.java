package com.wzb.web.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:DeFerredResulterHolder  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/3 15:13   <br/>
 */

@Component
public class DeferredResultHolder {

    /**
     * 1. 第一个参数的String是订单号, 第二个是请求信息
     */
    private Map<String, DeferredResult<String>> map = new HashMap<>();

    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }
}
