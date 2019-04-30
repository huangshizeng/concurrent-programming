package com.huang.thread;

/**
 * @author hsz
 */


public class ThreadLocalDemo {

    ThreadLocal<Long> threadLocal1 = new ThreadLocal();
    ThreadLocal<String> threadLocal2 = new ThreadLocal();

    public void set() {
        threadLocal1.set(Thread.currentThread().getId());
        threadLocal2.set(Thread.currentThread().getName());
    }

    public void get() {
        System.out.println(threadLocal1.get());
        System.out.println(threadLocal2.get());
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
        threadLocalDemo.set();
        threadLocalDemo.get();
        Thread thread = new Thread(() -> {
            threadLocalDemo.set();
            threadLocalDemo.get();
        });
        thread.start();
        thread.join();
        threadLocalDemo.get();
    }
}
