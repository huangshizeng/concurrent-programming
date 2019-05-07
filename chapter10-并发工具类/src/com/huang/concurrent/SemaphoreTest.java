package com.huang.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author hsz
 */


public class SemaphoreTest {

    // 30个线程
    private static ExecutorService threadPool = Executors.newFixedThreadPool(30);

    // 同时只能有10个线程在跑
    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            threadPool.execute(() -> {
                try {
                    // 可以看作颁发许可证
                    semaphore.acquire();
                    Thread.sleep(100);
                    System.out.println("模拟处理");
                    // 可以看作归还许可证，此时另一个线程就可以获得许可证来执行
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }
}
