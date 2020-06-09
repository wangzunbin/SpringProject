package com.wangzunbin.java8._14_completableFuture;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName:CompletableFutureInAction2  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/10 0:05   <br/>
 */
public class CompletableFutureInAction2 {

    public static void main(String[] args)  throws InterruptedException {

        AtomicBoolean finished = new AtomicBoolean(false);
        ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            // 设置为false, 就不是守护线程了, 一定拿到值(需要手动shutdown的线程池), 但是设置为true的话就是守护线程, 是可以不用等到结果输出
            t.setDaemon(false);
            return t;
        });

        /*
            不加这个executor, 就用主线程来作为守护线程, 但是主线程执行完就短了, 它也跟着死掉了, 加了线程池, 就会使用线程池的线程, 而不是主线程
         */
        CompletableFuture.supplyAsync(CompletableFutureInAction1::get, executor)
                .whenComplete((v, t) -> {
                    Optional.of(v).ifPresent(System.out::println);
                    finished.set(true);
                });

        System.out.println("====i am no ---block----");
/*        while (!finished.get()) {
            Thread.sleep(1);
        }*/
//        executor.shutdown();
    }
}
