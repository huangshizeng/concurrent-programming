package com.huang.concurrent.thread;

/**
 * @author hsz
 */

public class ThreadDemo extends Thread {

    private String name;
    private int ticket = 5;

    public ThreadDemo(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
                System.out.println("thread = " + this.name + ", 剩ticket = " + --ticket);
            }
        }
    }

    public static void main(String[] args) {
        ThreadDemo t1 = new ThreadDemo("线程1");
        ThreadDemo t2 = new ThreadDemo("线程2");
        ThreadDemo t3 = new ThreadDemo("线程3");
        t1.start();
        t2.start();
        t3.start();
    }
}
