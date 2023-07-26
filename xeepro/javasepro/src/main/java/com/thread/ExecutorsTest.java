package com.thread;

import java.util.concurrent.*;

/**
 * 线程池的用法
 * <p>
 * 固定大小的线程池：Executors.newFixedThreadPool()方法创建的线程池具有固定的线程数。
 * 如果任务数量超过线程池的最大容量，那么任务将会被放入队列中等待执行。如果队列中的任务堆积过多，
 * 可能会导致内存溢出或者应用程序崩溃。
 * <p>
 * 缓存线程池：Executors.newCachedThreadPool()方法创建的线程池可以根据需要动态地创建新的线程，
 * 但是没有限制线程的数量。这意味着当任务数量非常大时，线程池可能会创建大量的线程，从而消耗大量
 * 的系统资源。
 * <p>
 * 单线程线程池：Executors.newSingleThreadExecutor()方法创建的线程池只有一个工作线程，适用于需要
 * 保证任务按照顺序执行的场景。然而，如果该线程由于异常终止而结束，线程池会创建一个新的线程来替代它。
 * 这可能会导致无限制地创建新线程，从而耗尽系统资源。
 * <p>
 * 隐藏线程池的细节：Executors类隐藏了线程池的一些细节，例如线程池的最大容量、队列类型等。
 * 这可能导致在某些情况下无法满足特定的需求，例如需要限制线程池的大小或者使用特定类型的队列。
 */
public class ExecutorsTest {

    public void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(512);

        /**
         * 为避免OOM，我们应该使用ThreadPoolExecutor的构造方法手动指定队列的最大长度
         *
         *  corePoolSize和maximumPoolSize设置不当会影响效率，甚至耗尽线程；
         *  workQueue设置不当容易导致OOM；
         *  handler设置不当会导致提交任务时抛出异常。
         *
         *  线程池的工作顺序(提交顺序)
         *  corePoolSize -> 任务队列 -> maximumPoolSize -> 拒绝策略
         *
         *  要区分提交优先级和执行优先级
         *
         *  拒绝策略
         *      拒绝策略	                        拒绝行为
         *  AbortPolicy	                    抛出RejectedExecutionException
         *  DiscardPolicy	                什么也不做，直接忽略
         *  DiscardOldestPolicy	            丢弃执行队列中最老的任务，尝试为当前提交的任务腾出位置
         *  CallerRunsPolicy	            直接由提交任务者执行这个任务
         *
         *  线程池默认的拒绝行为是AbortPolicy，也就是抛出RejectedExecutionHandler异常，该异常是非受检异常，容易忘记捕获。
         *  如果不关心任务被拒绝的事件，可以将拒绝策略设置成DiscardPolicy，这样多余的任务会悄悄的被忽略。
         *
         *  cpu密集型：主要计算    cpu + 1
         *  io密集型：文件读取，网络，数据库操作    cpu * 2
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,  // 核心线程数，线程池长期维护的线程数，不会回收
                20, // 线程数的上线，非核心线程数 =  此值 - 核心线程数
                0L, // 线程存活时间，非核心线程数超过这个时间会被回收
                TimeUnit.SECONDS,
                queue, // 任务的排队队列，使用有界队列，避免OOM
                new ThreadPoolExecutor.AbortPolicy()  // 拒绝策略
        );
    }

}
