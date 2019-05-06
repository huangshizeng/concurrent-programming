package com.huang.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hsz
 */


public class AtomicIntegerDemo1 {

    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
    }
}
