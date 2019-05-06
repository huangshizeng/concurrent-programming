package com.huang.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author hsz
 */


public class CountDownLatchTest {

    /**
     * 使用join让主线程等待子线程执行完
     */
    public static void joinTest() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1执行完");
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2执行完");
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("主线程执行完");
    }

    /**
     * 使用CountDownLatch让主线程等待子线程执行完
     *
     * @throws InterruptedException
     */
    public static void countDownLatchTest() throws InterruptedException {
        CountDownLatch c = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1执行完");
            c.countDown();
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2执行完");
            c.countDown();
        });
        t1.start();
        t2.start();
        c.await();
        System.out.println("主线程执行完");
    }

    public static void main(String[] args) throws InterruptedException {
        //CountDownLatchTest.joinTest();
        CountDownLatchTest.countDownLatchTest();
    }
}
