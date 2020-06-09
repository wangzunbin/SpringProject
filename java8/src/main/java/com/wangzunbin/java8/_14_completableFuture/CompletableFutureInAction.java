package com.wangzunbin.java8._14_completableFuture;

import java.util.Random;

/**
 * ClassName:CompletableFutureInAction  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/9 23:54   <br/>
 */
public class CompletableFutureInAction {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
             /*CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(10000L);
                future.complete(1000d);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }

        }).start();

        System.out.println("..............");
        future.whenComplete((v, t) -> {
            System.out.println(v);
            t.printStackTrace();
        });*/

        /*CompletableFuture<Double> future = CompletableFuture.supplyAsync(CompletableFutureInAction::get);

        future.whenComplete((v, t) -> {
            System.out.println(v);
            t.printStackTrace();
        });*/

        // 多个
        /*long start = System.currentTimeMillis();
        List<Double> doubles = Arrays.asList(random.nextDouble(), random.nextDouble(), random.nextDouble(), random.nextDouble(), random.nextDouble());
        System.out.println(doubles);
        List<CompletableFuture<Double>> futures = doubles
                .stream()
                .map(d -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(10000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return d * 10d;
                }))
                .collect(toList());

        List<Double> collect = futures.stream().parallel().map(CompletableFuture::join).collect(toList());
        System.out.println(collect);
        System.out.println(start-System.currentTimeMillis());
*/
        /*Executor executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        executor.execute(() -> System.out.println("sfsdfsfs"));*/
    }
}
