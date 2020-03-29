package com.wzb.web.async;

import org.springframework.stereotype.Component;

/**
 * ClassName:MockQueue  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/3 8:54   <br/>
 */

@Component
public class MockQueue {

    // 下单的消息
    private String placeOrder;

    // 订单小心
    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder)   {
      new Thread(() -> {
          System.out.println("接到下单请求" + placeOrder);
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          this.completeOrder = placeOrder;
          System.out.println("订单处理完毕: " + placeOrder);
      }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
