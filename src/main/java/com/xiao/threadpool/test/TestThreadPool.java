package com.xiao.threadpool.test;

import com.xiao.threadpool.ThreadTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {
    public static void main(String[] args) {
        //corePoolSize 核心线程数，会一直存活
        // maxpoolSize,keepAliveTime,TimeUnit,queueCapacity
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(100)); //线程队列
        //当核心线程数+线程队列满足不了业务才会去使用新线程
        for(int i=0;i<120;i++){
            ThreadTask myTask = new ThreadTask(i);
            executor.execute(myTask);
            System.out.println("当前线程池线程数量："+executor.getPoolSize()+" 当前线程队列大小："+
                    executor.getQueue().size()+" 当前线程池已结束线程数量："+executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }

}
