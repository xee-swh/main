package com.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable接口实现多线程
 * 与FutureTask配合使用，可以取得线程的执行结果
 */
public class TestCallable implements Callable<String> {

    public String call() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("test" + i);
        }
        return "finish";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(new TestCallable());
        new Thread(futureTask).start();

        System.out.println("result: -------" + futureTask.get());

        System.out.println("done: -------" +  futureTask.isDone());
    }

}
