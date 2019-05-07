package com.huang.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hsz
 */

public class ConditionDemo {

    public static void main(String[] args) {
        Service service = new Service();
        Thread t1 = new Thread(new MyThreadA(service));
        Thread t2 = new Thread(new MyThreadB(service));
        t1.start();
        t2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.signalA();
        //service.signalB();
    }

    static class MyThreadA implements Runnable {
        private Service service;

        public MyThreadA(Service service) {
            this.service = service;
        }

        @Override
        public void run() {
            service.awaitA();
        }
    }

    static class MyThreadB implements Runnable {
        private Service service;

        public MyThreadB(Service service) {
            this.service = service;
        }

        @Override
        public void run() {
            service.awaitB();
        }
    }

    static class Service {
        private Lock lock = new ReentrantLock();
        private Condition conditionA = lock.newCondition();
        private Condition conditionB = lock.newCondition();

        public void awaitA() {
            lock.lock();
            try {
                System.out.println("A睡眠");
                conditionA.await();
                System.out.println("A已醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void signalA() {
            lock.lock();
            try {
                conditionA.signal();
                System.out.println("去唤醒A");
            } finally {
                lock.unlock();
            }
        }

        public void awaitB() {
            lock.lock();
            try {
                System.out.println("B睡眠");
                conditionB.await();
                System.out.println("B已醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void signalB() {
            lock.lock();
            try {
                conditionB.signal();
                System.out.println("去唤醒B");
            } finally {
                lock.unlock();
            }
        }
    }
}
