package com.concurrent.threadPool;

import org.junit.Test;

import java.nio.file.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 实现描述: ForkJoinPoolTest
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-24 09:58
 */
public class ForkJoinPoolTest extends RecursiveTask<Integer> {
    private Path dir;

    public ForkJoinPoolTest() {
    }

    ForkJoinPoolTest(Path dir) {
        this.dir = dir;
    }

    @Test
    public void testAll() {
        Integer count = new ForkJoinPool().invoke(new ForkJoinPoolTest(Paths.get("/usr")));
        System.out.println(count);
    }

    @Override
    protected Integer compute() {
        int count = 0;
        try {
            DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
            for (Path subPath : ds) {
                if (!Files.isDirectory(subPath, LinkOption.NOFOLLOW_LINKS)) {
                    count++;
                } else {
                    ForkJoinPoolTest subTask = new ForkJoinPoolTest(subPath);
                    subTask.fork();
                    count += subTask.join();
                }
            }
        } catch (Exception ex) {
            return 0;
        }
        return count;
    }
}

