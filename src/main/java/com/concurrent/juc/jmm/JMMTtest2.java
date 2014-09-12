package com.concurrent.juc.jmm;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 实现描述: JMMTtest2,测试java编辑器指令重排序
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-17 00:46
 */
public class JMMTtest2 {
    private int a = 0;
    private boolean flag = false;

    public void write() {
        a = 1;
        flag = true;
    }

    public void read() {
        if (flag) {
            int i = a * a;
            System.out.println(String.format("flag:%s,i:%s", flag, i));
        } else {
            System.out.println(String.format("flag:%s,a:%s", flag, a));
        }
    }

    @Test
    public void testJMM() throws Exception {
        // 初始化对象
        final JMMTtest2 jmmTtest2 = new JMMTtest2();

        // 两个线程轮流执行
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                jmmTtest2.write();
                try {
                    TimeUnit.SECONDS.sleep(Long.valueOf(String.valueOf(new Random().nextInt(10))));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jmmTtest2.read();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                jmmTtest2.write();
                try {
                    TimeUnit.SECONDS.sleep(Long.valueOf(String.valueOf(new Random().nextInt(10))));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jmmTtest2.read();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
