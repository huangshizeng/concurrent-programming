package com.huang.concurrent.thread.example1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 使用wait和notify实现生产者消费者实例
 *
 * @author hsz
 */

class Box {

    private Queue<String> queue;
    private int size;

    public Box(Queue<String> queue, int size) {
        this.queue = queue;
        this.size = size;
    }

    public Queue<String> getQueue() {
        return queue;
    }

    public int getSize() {
        return size;
    }
}

class Producer implements Runnable {
    private final Box box;

    public Producer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            synchronized (box) {
                while (box.getQueue().size() == box.getSize()) {
                    try {
                        box.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                box.getQueue().offer("apple");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("往箱子中放入一个苹果，总共有" + box.getQueue().size() + "个苹果");
                box.notify();
            }
        }
    }
}

class Consumer implements Runnable {
    private final Box box;

    public Consumer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            synchronized (box) {
                while (box.getQueue().size() == 0) {
                    try {
                        box.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                box.getQueue().poll();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("    从箱子中拿走一个苹果，还剩" + box.getQueue().size() + "个苹果");
                box.notify();
            }
        }
    }
}

public class ProCon1 {

    public static void main(String[] args) {
        Box box = new Box(new LinkedList<String>(), 5);
        Producer producer = new Producer(box);
        Consumer consumer = new Consumer(box);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
