package com.anxu.livi.threadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "Callable工人：" + Thread.currentThread().getName() + " 干活中...";
    }
}
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> task = new MyCallable();
        Future<String> future = Executors.newSingleThreadExecutor().submit(task);
        String res = future.get();
        System.out.println(res);
    }
}
