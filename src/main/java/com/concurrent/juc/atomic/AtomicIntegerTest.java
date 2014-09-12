package com.concurrent.juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * 实现描述: AtomicIntegerTest
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-15 13:33
 */
public class AtomicIntegerTest {
    @Test
    public void testAll() throws InterruptedException {
        final AtomicInteger value = new AtomicInteger(10);
        assertEquals(value.compareAndSet(1, 2), false);
        assertEquals(value.get(), 10);
        assertTrue(value.compareAndSet(10, 3));
        assertEquals(value.get(), 3);
        value.set(0);

        //
        assertEquals(value.incrementAndGet(), 1);
        assertEquals(value.getAndAdd(2), 1);
        assertEquals(value.getAndSet(5), 3);
        assertEquals(value.get(), 5);

        // 10个线程执行++操作
        final int threadSize = 10;
        Thread[] ts = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            ts[i] = new Thread() {
                public void run() {
                    value.incrementAndGet();
                }
            };
        }
        //
        for (Thread t : ts) {
            t.start();
        }
        for (Thread t : ts) {
            t.join();
        }
        assertEquals(value.get(), 5 + threadSize);
    }
}
