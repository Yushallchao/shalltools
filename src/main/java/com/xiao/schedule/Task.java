package com.xiao.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Task {

    //定时任务
    public static void showTimer() {

        TimerTask task  = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println("时间=" + new Date() + " 执行了" + "你好！"); // 1次
            }
        };

        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的12:30:00执行，
        calendar.set(year, month, day, 9, 21, 00);
        Date date = calendar.getTime();
        Timer timer = new Timer();
        System.out.println(date);

        int period = 1000;
        //int period = 30 * 1000;
        //每天的date时刻执行task，每隔一天重复执行
        timer.schedule(task, date, period);
        //每天的date时刻执行task, 仅执行一次
        //timer.schedule(task, date);
    }
}
