package com.huang.concurrent;

import java.util.concurrent.*;

/**
 * 模拟银行流水处理服务
 *
 * @author hsz
 */

public class CyclicBarrierTest implements Runnable {

    // 创建4个屏障，处理完之后执行当前类的run方法
    private CyclicBarrier c = new CyclicBarrier(4, this);

    // 假设4个用户，一个线程处理一个用户的流水
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    // 存放每个用户计算出的流水结果
    private ConcurrentHashMap<String, Integer> count = new ConcurrentHashMap<>(4);

    public void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                // 模拟计算，每个用户都是10
                count.put(Thread.currentThread().getName(), 10);
                try {
                    // 计算完成，插入一个屏障
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {
        // 汇总结果
        long result = count.values().stream().reduce(Integer::sum).orElse(0);
        System.out.println(result);
    }

    public static void main(String[] args) {
        CyclicBarrierTest cyclicBarrierTest = new CyclicBarrierTest();
        cyclicBarrierTest.count();
        cyclicBarrierTest.executor.shutdown();
    }
}
