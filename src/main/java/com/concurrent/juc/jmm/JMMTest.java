package com.concurrent.juc.jmm;

import org.junit.Test;

/**
 * 实现描述: JMMTest，了解java内存模型
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-17 00:24
 */
public class JMMTest {
    private int x = 0, y = 0;
    private int a = 0, b = 0;

    @Test
    public void testJMM() throws Exception {

        // 开启两个线程
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                a = 1;
                x = b;
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                b = 1;
                y = a;
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // 获取x，y值
        System.out.println("x=" + x);
        System.out.println("y=" + y);
    }
}
