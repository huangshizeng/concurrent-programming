package com.huang.concurrent;

/**
 * volatile可以禁止重排序，并可应用于状态标记量
 *
 * @author hsz
 */


public class VolatileDemo {

    private static volatile boolean flag = false;
    private static volatile int num = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (flag) {
                    System.out.println(num);
                }
                System.out.println("read thread...");
            }
        });
        t1.start();
        new Thread(() -> {
            num = 1;
            flag = true;
            System.out.println("write thread completed");
        }).start();
        Thread.sleep(10);
        t1.interrupt();
    }
}
