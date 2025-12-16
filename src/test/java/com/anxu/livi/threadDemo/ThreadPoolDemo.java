package com.anxu.livi.threadDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池测试类
 *
 * @Author: haoanxu
 * @Date: 2025/11/24 17:53
 */
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        // 创建线程池-固定5个线程
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 提交任务给线程池
        for (int i = 0; i < 100; i++) {
            int taskId = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("任务" + taskId + "被线程" + Thread.currentThread().getName() + "执行");
                }
            });
        }

        // 关键步骤1：关闭线程池（禁止提交新任务）
        threadPool.shutdown();

        // 关键步骤2：等待线程池所有任务执行完毕（主线程阻塞）
        // 参数说明：等待1小时（足够任务执行完），时间单位为小时
        boolean allDone = threadPool.awaitTermination(1, TimeUnit.HOURS);

        // 任务全部执行完后，再计算耗时
        long endTime = System.currentTimeMillis();

        if (allDone) {
            System.out.println("所有任务执行完毕，耗时：" + (endTime - startTime) + "ms");
        } else {
            System.out.println("等待超时，部分任务可能未执行完");
        }
    }
}
