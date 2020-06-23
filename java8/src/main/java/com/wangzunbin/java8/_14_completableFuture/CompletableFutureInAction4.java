package com.wangzunbin.java8._14_completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * ClassName:CompletableFutureInAction4  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/10 0:35   <br/>
 */
public class CompletableFutureInAction4 {

    public static void main(String[] args) throws InterruptedException {

        /*CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> Integer.sum(i, 10))
                .whenComplete((v, t) -> System.out.println(v));*/

/*        CompletableFuture.supplyAsync(() -> 1)
                .handle((v, t) -> Integer.sum(v, 10))
                .whenComplete((v, t) -> System.out.println(v))
                .thenRun(System.out::println);*/
/*
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> Integer.sum(i, 10))
                .thenAccept(System.out::println);*/
/*
        CompletableFuture.supplyAsync(() -> 1)
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> 10 * i))
                .thenAccept(System.out::println);*/

/*        CompletableFuture.supplyAsync(() -> 1)
                .thenCombine(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> r1 + r2)
                .thenAccept(System.out::println);*/

        CompletableFuture.supplyAsync(() -> 1)
                // 同时返回
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> {
                    System.out.println(r1);
                    System.out.println(r2);
                    System.out.println(r1 + r2);
                });

        Thread.sleep(1000L);
    }
}
