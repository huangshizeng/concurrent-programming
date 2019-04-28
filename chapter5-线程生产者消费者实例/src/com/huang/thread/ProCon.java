package com.huang.thread;

/**
 * @author hsz
 */

class Info {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Producer implements Runnable {
    private Info info;

    public Producer(Info info) {
        this.info = info;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {

        }
    }
}

public class ProCon {
}
