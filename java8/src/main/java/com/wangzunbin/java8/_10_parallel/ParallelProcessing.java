package com.wangzunbin.java8._10_parallel;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * ClassName:ParallelProcessing  <br/>
 * Function: LongStream.rangeClosed 先准备数据, 不像iterate一个一个生产出来 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/6/7 18:05   <br/>
 */

public class ParallelProcessing {


    public static void main(String[] args) {
        System.out.println("The best process time(normalAdd)=>" + measureSumPerformance(ParallelProcessing::normalAdd, 100_000_000) + " MS");
//        System.out.println("The best process time(iterateStream)=>" + measureSumPerformance(ParallelProcessing::iterateStream, 10_000_000) + " MS");
//        System.out.println("The best process time(parallelStream)=>" + measureSumPerformance(ParallelProcessing::parallelStream, 10_000_000) + " MS");
        System.out.println("The best process time(parallelStream2)=>" + measureSumPerformance(ParallelProcessing::parallelStream2, 10_000_000) + " MS");
//        System.out.println("The best process time(parallelStream3)=>" + measureSumPerformance(ParallelProcessing::parallelStream3, 100_000_000) + " MS");
    }

    private static long measureSumPerformance(Function<Long, Long> adder, long limit) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long startTimestamp = System.currentTimeMillis();
            long result = adder.apply(limit);
            long duration = System.currentTimeMillis() - startTimestamp;
//            System.out.println("The result of sum=>" + result);
            if (duration < fastest) fastest = duration;
        }

        return fastest;
    }


    private static long iterateStream(long limit) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long parallelStream(long limit) {
        return Stream.iterate(1L, i -> i + 1).parallel()
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long parallelStream2(long limit) {
        return Stream.iterate(1L, i -> i + 1).mapToLong(Long::longValue).parallel()
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long parallelStream3(long limit) {
        return LongStream.rangeClosed(1, limit).parallel().reduce(0L, Long::sum);
    }

    private static long normalAdd(long limit) {
        long result = 0L;
        for (long i = 1L; i < limit; i++) {
            result += i;
        }
        return result;
    }
}
//      这里实际上有两个问题：
//
//     iterate生成的是装箱的对象，必须拆箱成数字才能求和。
//     我们很难把iterate分成独立块来并行执行。
//     第二个问题更有意思，因为你必须意识到某些流操作比其他操作更容易并行化。具体来说，iterate很难分割成能够独立执行的小块，因为每次应用这个函数都需要依赖前一次应用的结果。
//
//     整张数字列表在归纳过程中并没有准备好，因而无法有效地把流划分为小块来并行处理。把流标记成并行其实是给顺序处理增加了开销，它还要每次求和操作分到一个不同的线程上。
//
//     这说明并行编程可能很复杂，有时候甚至有点违反直觉。如果用得不对（比如采用了一个不易并行化的操作，如iterate）它甚至可能让程序的整体性能更差，所在调用那个看似神奇的parallel操作时，了解背后到底发生了什么是很有必要的。
//
//     那到底要怎么利用多核处理器，用流来高效地并行求和呢？
//
//     LongStream.rangeClosed这个方法与iterate相比有两个优点
//
//     LongStream.rangeClosed直接产生原始类型的long数字，没有装箱拆箱的开销。
//     LongStream.rangeClosed会生成数字范围，很容易拆分为独立的小块。例如，范围1到20可分为1到5、6到10、11到15和16到20。
//     终于，我们得到了一个比顺序执行更快的并行归纳。在Stream内部分成了几块。因此可以对不同的快独立并行进行归纳操作，最后同一个归纳操作会将各个子流的部分归纳结果合并起来，得到整个原始流的归纳结果。
//
//     并行化并不是没有代价的。并行化过程本身需要对流做递归划分，把每个子流的归纳操作分配到不同的线程，然后把这些操作的结果合并成一个值。但在多个内核之间移动数据的代价也可能比你想的还要大，所以很重要的一点是要保证在内核中并行执行工作的时间比在内核之间传输数据的时间长，你必须确保用得对；如果结果错了，算得快就毫无意义了。
//
//     个人理解，LongStream.range 方法生成的数字范围默认增量是1，这样只需要知道开始和结束即可立即生成数字范围，而Stream.iterate 增量可以是1，也可以是其他的数值，生成一下个数必须依赖于上一个，即便增量是1生成逻辑并没有变。
