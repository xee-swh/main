package com.concurrent;

/**
 * ThreadLocal
 * <p>
 * 在Java的多线程并发执行过程中，为了保证多个线程对变量的安全访问，可以将变量放到ThreadLocal类型的对象中，
 * 使变量在每个线程中都有独立值，不会出现一个线程读取变量时被另一个线程修改的现象。
 * <p>
 * ThreadLocal实例，在访问这个变量的值时，每个线程都会拥有一个独立的、自己的本地值。
 * 线程本地变量可以看成专属于线程的变量，不受其他线程干扰，保存着线程的专属数据。
 * <p>
 * ThreadLocal的主要价值在于线程隔离，ThreadLocal中的数据只属于当前线程，其本地值对别的线程是不可见的，
 * 在多线程环境下，可以防止自己的变量被其他线程篡改。
 * <p>
 * 场景：可以为每个线程绑定一个用户会话信息、数据库连接、HTTP请求等，
 * 这样一个线程所有调用到的处理函数都可以非常方便地访问这些资源。
 */
public class ThreadLocalTest {
    private static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
                THREAD_LOCAL.set("run1");
                System.out.println(THREAD_LOCAL.get());
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
                 THREAD_LOCAL.set("run2");
                System.out.println(THREAD_LOCAL.get());
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
                // THREAD_LOCAL.set("run3");
                System.out.println(THREAD_LOCAL.get());
            }
        }).start();
    }

}
