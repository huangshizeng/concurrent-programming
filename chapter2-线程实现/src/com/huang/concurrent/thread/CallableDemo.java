package com.huang.concurrent.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author hsz
 */

public class CallableDemo implements Callable {

    private String name;

    public CallableDemo(String name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("task" + name + "开始进行计算");
        Thread.sleep(3000);
        int sum = new Random().nextInt(300);
        int result = 0;
        for (int i = 0; i < sum; i++) {
            result += i;
        }
        return result;
    }

    public static void main(String[] args) {
        CallableDemo myThread = new CallableDemo("线程1");
        FutureTask<Integer> result = new FutureTask<Integer>(myThread);
        Thread thread1 = new Thread(result);
        thread1.start();
        try {
            System.out.println("结果：" + result.get()); //会阻塞主线程
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("结束");
    }
}
