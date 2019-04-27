package com.huang.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author hsz
 */

public class ThreadPoolExecutorRejectNewTaskDemo {

    static Logger log = Logger.getLogger(ThreadPoolExecutorRejectNewTaskDemo.class.getName());

    // 线程池核心池容量
    private static final int CORE_POOL_SIZE = 3;
    // 线程池最大容量
    private static final int MAX_POOL_SIZE = 6;
    // 阻塞队列的容量
    private static final int QUEUE_CAPACITY = 3;
    // 非核心线程处于空闲状态的最长时间
    private static final int KEEP_ALIVE_TIME_VALUE = 1000;
    // 线程池对象
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME_VALUE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(QUEUE_CAPACITY),
            new MyThreadFactory());

    public static void main(String[] args) {
        // 模拟线程池及其内部的队列都已满后, 继续向其提交新任务将会被拒绝的场景
        threadPoolFullToRejectNewTask();

        // 模拟线程池被关闭(shutdown)后, 继续向其提交新任务将会被拒绝的场景
        //shutdownThreadPoolToRejectNewTask();
    }

    /**
     * 模拟线程池被关闭(shutdown)后, 继续向其提交新任务将会被拒绝的场景
     */
//    private static void shutdownThreadPoolToRejectNewTask() {
//        MyRunnable r = new MyRunnable("");
//
//        int cycleCount = Math.max(MAX_POOL_SIZE - 1, 0);
//
//        // 先提交(MAX_POOL_SIZE - 1)个任务. 显然, 线程池此时还未满
//        for (int i = 0; i < cycleCount; i++) {
//            log.info("提交任务" + i);
//            THREAD_POOL_EXECUTOR.execute(r);
//        }
//        // 在线程池未满的情况下关闭线程池.
//        THREAD_POOL_EXECUTOR.shutdown();
//
//        // 在线程池已处于关闭(SHUTDOWN)的状态下
//        if (THREAD_POOL_EXECUTOR.isShutdown()) {
//            try {
//                System.out.println("提交任务" + cycleCount);
//                Thread.sleep(10);
//                // 在线程池未满但却已经关闭了的情况下, 继续向该线程池中提交任务.
//                THREAD_POOL_EXECUTOR.execute(r);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * 模拟线程池及其内部的队列都已满后, 继续向其提交新任务将会被拒绝的场景
     */
    private static void threadPoolFullToRejectNewTask() {
        // 循环提交任务的总次数. 该总次数等于"线程池的最大线程容量和阻塞队列的容量之和", 在执行完
        // 该循环后, 线程池和阻塞队列都已满.
        int cycleCount = 7;

        for (int i = 0; i < cycleCount; i++) {
            String name = "任务" + (i + 1);
            MyRunnable r = new MyRunnable(name);
            log.info("提交任务:" + name);
            THREAD_POOL_EXECUTOR.execute(r);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 当前已提交的任务数
        int tasksCount = cycleCount;

        // 在线程池和阻塞队列都已满的情况下, 继续提交任务.
//        try {
//            Thread.sleep(10000);
//            String name = "任务6";
//            MyRunnable r = new MyRunnable(name);
//            System.out.println("提交任务数" + (tasksCount));
//            Thread.sleep(10);
//            THREAD_POOL_EXECUTOR.execute(r);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 自定义的线程工厂类, 用于为线程池创建线程对象.
     */
    private static class MyThreadFactory implements ThreadFactory {
        static int threadNumber = 1;

        @Override
        public Thread newThread(Runnable r) {
            String threadName = "thread-" + (threadNumber++);
            log.info("创建线程 " + threadName);
            return new Thread(r, threadName);
        }
    }

    /**
     * 表示向线程池提交的任务的 Runnable实现类
     */
    private static class MyRunnable implements Runnable {
        private String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                log.info("执行任务-" + name);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}