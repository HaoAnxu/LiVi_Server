package com.anxu.smarthomeunity.threadDemo;

class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("MyRunnable 线程"+Thread.currentThread().getName()+"正在运行");
    }
}
public class RunnableDemo {
    public static void main(String[] args) {
        Runnable task = new MyRunnable();
        Thread thread1 = new Thread(task,"工人A");
        thread1.start();
        Thread thread2 = new Thread(task,"工人B");
        thread2.start();
        Thread thread3 = new Thread(task,"工人C");
        thread3.start();
    }
}
