package com.concurrent.lock;

import org.junit.Test;

/**
 * 实现描述: DeadLockTest
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-16 19:18
 */
public class DeadLockTest {

    @Test
    public void testSwapDeadLock() throws Exception {
        // 创建两个对象，用于互相交互
        final Cell c1 = new Cell(100);
        final Cell c2 = new Cell(200);

        // c1-c2
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                long count = 0;
                try {
                    while (true) {
                        c1.swapSequance(c2);
                        count++;
                        if (count % 100 == 0) {
                            System.out.println("thread1's current progress: " + count);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setName("thread1");

        //c2-c1
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                long count = 0;
                try {
                    while (true) {
                        c2.swap(c1);
                        count++;
                        if (count % 100 == 0) {
                            System.out.println("thread2's current progress: " + count);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t2.setName("thread2");

        //
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    @Test
    public void testSwapSequance() throws Exception {
        // 创建两个对象，用于互相交互
        final Cell c1 = new Cell(100);
        final Cell c2 = new Cell(200);

        // c1-c2
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                long count = 0;
                try {
                    while (true) {
                        c1.swapSequance(c2);
                        count++;
                        if (count % 100 == 0) {
                            System.out.println("thread1's current progress: " + count);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setName("thread1");

        //c2-c1
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                long count = 0;
                try {
                    while (true) {
                        c2.swap(c1);
                        count++;
                        if (count % 100 == 0) {
                            System.out.println("thread2's current progress: " + count);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t2.setName("thread2");

        //
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }


    public class Cell {
        private long value;

        public Cell(long value) {
            this.value = value;
        }

        public synchronized long getValue() {
            return value;
        }

        public synchronized void setValue(long value) {
            this.value = value;
        }

        /**
         * 同步锁定
         * @param other
         */
        public synchronized void swap(Cell other) {
            long t = getValue();
            long v = other.getValue();
            setValue(v);
            other.setValue(t);
        }

        /**
         * 顺序同步锁定
         * @param other
         */
        public void swapSequance(Cell other) {
            if (this == other) return; // Alias check
            else if (System.identityHashCode(this) < System.identityHashCode(other)) {
                this.swap(other);
            } else {
                other.swap(this);
            }
        }

    }
}
