package com.wzb.web.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * ClassName:AsyncController  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/3 8:47   <br/>
 */
@RestController
@Slf4j
public class AsyncController {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    // 这是第一种的异步请求方法
//    @RequestMapping("/order")
//    public Callable<String> order() throws Exception {
//        Callable<String> callable = new Callable<String>() {
//
//            @Override
//            public String call() throws Exception {
//                log.info("主线程开始");
//                Thread.sleep(1000);
//                log.info("主线程返回");
//                return "success";
//            }
//        };
//        return callable;

    // 第二种是: 用了DeferredResult这种方法解决异步请求的方法
    @GetMapping("/order")
    public DeferredResult<String> order() throws Exception {
        log.info("主线程开始");
        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);

//        mockQueue.setCompleteOrder(orderNumber);
        log.info("主线程返回");
        return result;
    }



}
