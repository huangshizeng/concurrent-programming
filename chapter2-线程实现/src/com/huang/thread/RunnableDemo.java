package com.huang.thread;

/**
 * @author hsz
 */

public class RunnableDemo implements Runnable {

    private int ticket = 5;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
                System.out.println("thread = " + Thread.currentThread().getName() + ", 剩ticket = " + --ticket);
            }
        }
    }

    public static void main(String[] args) {
        RunnableDemo myThread = new RunnableDemo();
        Thread t1 = new Thread(myThread, "线程1");
        Thread t2 = new Thread(myThread, "线程2");
        Thread t3 = new Thread(myThread, "线程3");
        t1.start();
        t2.start();
        t3.start();
    }
}
