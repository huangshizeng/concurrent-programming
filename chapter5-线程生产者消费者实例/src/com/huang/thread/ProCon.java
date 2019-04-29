package com.huang.thread;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 使用wait和notify实现生产者消费者实例
 * @author hsz
 */

class Box {

    private Queue<String> queue;
    private int size;

    public Box(Queue<String> queue, int size) {
        this.queue = queue;
        this.size = size;
    }

    public synchronized void put(String apple) {
        while (queue.size() == size) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.offer(apple);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("往箱子中放入一个苹果，总共有" + queue.size() + "个苹果");
        super.notify();
    }

    public synchronized String get() {
        while (queue.size() == 0) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String apple = queue.poll();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("    从箱子中拿走一个苹果，还剩" + queue.size() + "个苹果");
        super.notify();
        return apple;
    }
}

class Producer implements Runnable {
    private Box box;

    public Producer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            box.put("apple");
        }
    }
}

class Consumer implements Runnable {
    private Box box;

    public Consumer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            String apple = box.get();
        }
    }
}

public class ProCon {

    public static void main(String[] args) {
        Box box = new Box(new LinkedList<String>(), 5);
        Producer producer = new Producer(box);
        Consumer consumer = new Consumer(box);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
