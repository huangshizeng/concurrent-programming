package com.huang.concurrent;

/**
 * @author hsz
 */

public class VolatileDemo2 {

    static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 10000; k++) {
                    count++;
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(count);
    }
}
