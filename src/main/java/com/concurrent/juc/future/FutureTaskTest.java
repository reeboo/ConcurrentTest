package com.concurrent.juc.future;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 实现描述: FutureTaskTest
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-23 21:25
 */
public class FutureTaskTest {

    @Test
    public void testAll() {

        // 创建执行框架
        ExecutorService faxExecutor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 5L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(50), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        // 批量创建10个任务
        List<RandomFutureTask> randomFutureTaskList = new ArrayList<RandomFutureTask>();
        for (int i = 0; i < 10; i++) {
            randomFutureTaskList.add(new RandomFutureTask());
        }

        // 获取结果
        try {
            List<Future<Object>> resultList = faxExecutor.invokeAll(randomFutureTaskList, 4, TimeUnit.SECONDS);
            for (Future<Object> result : resultList) {
                System.out.println(result.get());
            }
        } catch (Exception e) {
            // do nothing
        }
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
