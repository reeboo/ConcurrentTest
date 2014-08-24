package com.concurrent.cyclicberry;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CyclicBarrier类似于CountDownLatch也是个计数器，
 * 不同的是CyclicBarrier数的是调用了CyclicBarrier.await()进入等待的线程数，
 * 当线程数达到了CyclicBarrier初始时规定的数目时，所有进入等待状态的线程被唤醒并继续。
 * CyclicBarrier就象它名字的意思一样，可看成是个障碍，
 * 所有的线程必须到齐后才能一起通过这个障碍。
 * CyclicBarrier初始时还可带一个Runnable的参数，
 * 此Runnable任务在CyclicBarrier的数目达到后，所有其它线程被唤醒前被执行。
 * <p/>
 * @version v1.0.0
 *
 * @author: reeboo
 * @since: 2014-08-23 18:23
 */
public class CyclicBarrierTest {

    @Test
    public void testAll() throws Exception {

        // 这个团队中共有3个队员,即需要3个线程
        final CyclicBarrier cb = new CyclicBarrier(3);
        final CountDownLatch latch = new CountDownLatch(3 * 5);

        // 在线程池中放入三个线程
        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) { // 开启三个任务
            es.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        try {
                            Thread.sleep(new Random().nextInt(1000));
                            System.out.print(Thread.currentThread().getName()
                                    + "已到达集合点" + (i + 1) + "，现在共有"
                                    + (cb.getNumberWaiting() + 1) + "个线程到达");
                            // 如果有2个线程已经在等待，那么最后一个线程到达后就可以一起开始后面操作
                            if (cb.getNumberWaiting() + 1 == 3) {
                                System.out.println("，全部到齐，出发去下一个目标");
                            } else {
                                System.out.println("，正在等待");
                            }
                            cb.await();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    }
                }
            });
        }

        // 等待线程执行结束，关闭执行框架
        latch.await();
        es.shutdown();
    }

}
