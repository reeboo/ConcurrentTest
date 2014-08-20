package com.concurrent.jmm;

/**
 * 实现描述: SynchronizeTest
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-20 10:50
 */
public class SynchronizeTest {
    private int some;
    public int another;

    public int getSome() {
        return some;
    }

    public synchronized int getSomeWithSync() {
        return some;
    }

    public void setSome(int v) {
        some = v;
    }

    public synchronized void setSomeWithSync(int v) {
        some = v;
    }
}
