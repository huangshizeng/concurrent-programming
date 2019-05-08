package com.huang.concurrent.custom;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义共享锁，与Semaphore实现类似
 *
 * @author hsz
 */

public class SharedLock implements Lock {

    private int count;

    public SharedLock(int count) {
        this.count = count;
    }

    private static class Sync extends AbstractQueuedSynchronizer {

        Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException();
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current - reduceCount;
                // 若无锁可获时，直接返回负数，若因为CAS失败则循环再来
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int addCount) {
            // 死循环释放锁，确保成功释放锁
            for (; ; ) {
                int current = getState();
                int newCount = current + addCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }

        /**
         * 条件变量
         */
        Condition newCondition() {
            return new AbstractQueuedSynchronizer.ConditionObject();
        }
    }

    // 同时可以有count个线程执行
    private final Sync sync = new Sync(this.count);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.acquireShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
