package com.huang.thread.example2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author hsz
 */
class Box {

    private BlockingQueue<String> blockingQueue;

    public Box(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public BlockingQueue<String> getBlockingQueue() {
        return blockingQueue;
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
            try {
                box.getBlockingQueue().put("apple");
                System.out.println("往箱子中放入一个苹果");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            try {
                box.getBlockingQueue().take();
                Thread.sleep(200);
                System.out.println("    从箱子中拿走一个苹果");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


public class ProCon2 {

    public static void main(String[] args) {
        Box box = new Box(new ArrayBlockingQueue<String>(5));
        Producer producer = new Producer(box);
        Consumer consumer = new Consumer(box);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
