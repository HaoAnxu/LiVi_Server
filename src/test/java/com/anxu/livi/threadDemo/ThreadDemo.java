package com.anxu.livi.threadDemo;

// 1. 继承 Thread 类，重写 run() 方法（定义任务）
class MyThread extends Thread {
    @Override
    public void run() {
        // 工人要干的活：打印一句话
        System.out.println("Thread工人：" + Thread.currentThread().getName() + " 干活中...");
    }
}

// 2. 使用线程（让工人开工）
public class ThreadDemo {
    public static void main(String[] args) {
        MyThread worker1 = new MyThread();
        worker1.setName("工人A");
        worker1.start();
        MyThread worker2 = new MyThread();
        worker2.setName("工人B");
        worker2.start();
        MyThread worker3 = new MyThread();
        worker3.setName("工人C");
        worker3.start();
    }
}