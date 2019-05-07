package com.huang.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hsz
 */


public class LockDemo implements Runnable {

    private int ticket = 5;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            //获取不到锁时会进行等待
//            lock.lock();
//            lock.tryLock();//获取到锁返回true，获取不到返回false，通过if判断来决定是否往下执行
//            lock.lockInterruptibly();//获取不到锁时会进行等待，但可以被interrupt()方法打断抛出异常来结束阻塞
            try {
                if (ticket > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread = " + Thread.currentThread().getName() + ", 剩ticket = " + --ticket);
                }
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        LockDemo myThread = new LockDemo();
        Thread t1 = new Thread(myThread, "线程1");
        Thread t2 = new Thread(myThread, "线程2");
        Thread t3 = new Thread(myThread, "线程3");
        t1.start();
        t2.start();
        t3.start();
    }
}
