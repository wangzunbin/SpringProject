package com.wangzunbin.java8._14_completableFuture;

/**
 * ClassName:CompletableFutureInAction5  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/10 0:35   <br/>
 */
public class CompletableFutureInAction5 {

    public static void main(String[] args) throws InterruptedException {
        // 两个CompletionStage，都完成了计算才会执行下一步的操作（Runnable）
/*        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is running.");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is running.");
            return 2;
        }), () -> System.out.println("done"));*/

        //  两个CompletionStage，谁执行返回的结果快，我就用那个CompletionStage的结果进行下一步的转化操作。
 /*       CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1.get();
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1.get();
        }), v -> v * 10)
                .thenAccept(System.out::println);*/

        /*CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1.get();
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1.get();
        }), System.out::println);*/

       /* CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1.get();
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1.get();
        }), () -> System.out.println("done."));*/

    /*    List<CompletableFuture<Double>> collect = Arrays.asList(1, 2, 3, 4)
                .stream()
                .map(i -> CompletableFuture.supplyAsync(CompletableFutureInAction1::get))
                .collect(toList());

        CompletableFuture.anyOf(collect.toArray(new CompletableFuture[collect.size()]))
                .thenRun(() -> System.out.println("done"));


        Thread.currentThread().join();*/
    }
}
