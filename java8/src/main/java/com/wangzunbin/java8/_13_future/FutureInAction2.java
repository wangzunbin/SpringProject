package com.wangzunbin.java8._13_future;


import java.util.concurrent.*;

/**
 * ClassName:FutureInAction2  <br/>
 * Function: 用原有的jdk的Future和线程池实现多线程 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/6/7 18:05   <br/>
 */

public class FutureInAction2 {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException, TimeoutException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(10000L);
                return "I am finished.";
            } catch (InterruptedException e) {
                return "I am Error.";
            }
        });

        //...
        //...
        //...
        //...
        //String value = future.get(10, TimeUnit.MICROSECONDS);
        while (!future.isDone()) {
            Thread.sleep(10);
        }
        System.out.println(future.get());
        executorService.shutdown();
    }
}
