package com.xiao.threadpool;

public class ThreadTask implements Runnable {
    private int taskNum;

    public ThreadTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println("开始task "+taskNum);
        Thread.currentThread();
        System.out.println(Thread.currentThread());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束task "+taskNum);
    }
}
