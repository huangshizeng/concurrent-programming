package com.huang.thread.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hsz
 */


public class AtomicIntegerDemo2 {

    public AtomicInteger inc = new AtomicInteger(0);
    private static CountDownLatch c = new CountDownLatch(10);

    public void increase() {
        inc.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo2 test = new AtomicIntegerDemo2();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    test.increase();
                }
                c.countDown();
            });
            threads[i].start();
        }
        c.await();
        // 输出是100000
        System.out.println(test.inc);
    }
}
