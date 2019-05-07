package com.huang.concurrent.thread;

/**
 * @author hsz
 */

public class SynchronizedDemo implements Runnable {
    private int ticket = 5;

//    @Override
//    public synchronized void run() {
//        for (int i = 0; i < 10; i++) {
//            if (ticket > 0) {
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("thread = " + Thread.currentThread().getName() + ", 剩ticket = " + --ticket);
//            }
//        }
//    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (this) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread = " + Thread.currentThread().getName() + ", 剩ticket = " + --ticket);
                }
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedDemo myThread = new SynchronizedDemo();
        Thread t1 = new Thread(myThread, "线程1");
        Thread t2 = new Thread(myThread, "线程2");
        Thread t3 = new Thread(myThread, "线程3");
        t1.start();
        t2.start();
        t3.start();
    }
}
