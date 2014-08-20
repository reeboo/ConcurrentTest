package com.concurrent.jmm;

/**
 * 实现描述: SynchronizedTest2
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-20 10:55
 */
public class SynchronizedTest2 {
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private int some;

    public int getSome() {
        synchronized (lock1) {
            return some;
        }
    }

    public void setSome(int v) {
        synchronized (lock2) {
            some = v;
        }
    }
}
