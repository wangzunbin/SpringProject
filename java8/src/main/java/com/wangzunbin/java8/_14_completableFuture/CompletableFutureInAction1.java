package com.wangzunbin.java8._14_completableFuture;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * ClassName:CompletableFutureInAction  <br/>
 * Function: 异步回调 <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/9 23:58   <br/>
 */
public class CompletableFutureInAction1 {
    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {

        //supplyAsync
        CompletableFuture<Double> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            double value = get();
            completableFuture.complete(value);
        }).start();

        System.out.println("===no===block....");

        // 异步回调
        completableFuture.whenComplete((v, t) -> {
            Optional.ofNullable(v).ifPresent(System.out::println);
            Optional.ofNullable(t).ifPresent(Throwable::printStackTrace);
        });
    }

    static double get() {
        try {
            Thread.sleep(RANDOM.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double result = RANDOM.nextDouble();
        System.out.println(result);
        return result;
    }
}
