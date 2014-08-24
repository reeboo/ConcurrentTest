package com.concurrent.threadPool;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
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

    public ForkJoinPoolTest(Path dir) {
        this.dir = dir;
    }

    public static void main(String args[]) {
        Integer count = new ForkJoinPool().invoke(new ForkJoinPoolTest(Paths.get("/usr")));
        System.out.println(count);
    }

    @Override
    protected Integer compute() {
        int count = 0;
        try {
            DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
            for (Path subPath : ds) {
                if (Files.isDirectory(subPath, LinkOption.NOFOLLOW_LINKS)) {
                    ForkJoinPoolTest subTask = new ForkJoinPoolTest(subPath);
                    subTask.fork();
                    count += subTask.join();
                } else {
                    count++;
                }
            }
        } catch (Exception ex) {
            return 0;
        }
        return count;
    }
}

