package com.concurrent.juc.future;

import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 实现描述: 主线程等待指定时间，子线程异步执行
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-09-05 17:17
 */
public class ListenableExecutor {
    @Test
    public void testAll() {

        // 创建执行框架
        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 5L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(50), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        ListeningExecutorService service = MoreExecutors.listeningDecorator(executorService);

        // 批量创建10个任务
        final CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            ListenableFuture<Object> future = service.submit(new RandomFutureTask());
            Futures.addCallback(future,new FutureCallback<Object>(){
                @Override
                public void onSuccess(Object result) {
                    System.out.println(result);
                    latch.countDown();
                }

                @Override
                public void onFailure(Throwable t) {
                    latch.countDown();
                    t.printStackTrace();
                }
            });
        }


        // 等待4秒钟
        try {
            // 主线程等待特定时间，超时则不再等待，但任务仍执行
            latch.await(4, TimeUnit.SECONDS);
            System.out.println("---------------------------end");

            // check子线程是否都在执行
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 关闭容器
        service.shutdown();
    }

    /**
     * 0-5秒内执行完毕的任务
     */
    static class RandomFutureTask implements Callable<Object> {
        @Override
        public Object call() throws Exception {
            int sleepSecond = new Random().nextInt(5);
            TimeUnit.SECONDS.sleep(sleepSecond);
            return String.format("Thread %s sleeep %s ,exceute result:success", Thread.currentThread().getName(), sleepSecond);
        }
    }
}
