package com.concurrent.threadPool;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * 实现描述: PrimeTest
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-28 14:04
 */
public class PrimeTest {

    @Test
    public void testAll(){
        // 创建执行框架
        ExecutorService executorService = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 5L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1000000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        ListeningExecutorService service = MoreExecutors.listeningDecorator(executorService);
        double loopTime = Math.pow(10,10);

        // 求10的10幂以内的素数
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < loopTime; i++) {
            final int number = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    if(isPrime(number)){
                        System.out.println(number);
                    }
                }
            });
        }

        executorService.shutdown();
        System.out.println("total cost:"+(System.currentTimeMillis()-beginTime));
    }

    /**
     * 判断是否是素数
     * @param a
     * @return
     */
    public static boolean isPrime(int a) {
        boolean flag = true;
        if (a < 2) {// 素数不小于2
            return false;
        } else {
            for (int i = 2; i <= Math.sqrt(a); i++) {
                if (a % i == 0) {// 若能被整除，则说明不是素数，返回false
                    flag = false;
                    break;// 跳出循环
                }
            }
        }
        return flag;
    }
}
