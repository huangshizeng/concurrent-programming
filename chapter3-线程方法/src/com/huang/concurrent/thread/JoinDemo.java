package com.huang.concurrent.thread;

/**
 * @author hsz
 */

class MyThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("线程-" + Thread.currentThread().getName() + "运行-" + i);
        }
    }
}

public class JoinDemo {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread, "A");
        thread.start();
        for (int i = 0; i < 20; i++) {
            if (i > 10) {
                try {
                    thread.join(); // 等待线程A执行完后再执行main线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("线程-" + Thread.currentThread().getName() + "运行-" + i);
        }
    }
}
