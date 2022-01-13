package com.xiao;

import com.xiao.impl.Handler;
import com.xiao.impl.HandlerImpl;
import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        HandlerImpl handler1 = new HandlerImpl();
        handler1.Hello();
        System.out.println(handler1.hashCode());


        Handler handler = new HandlerImpl();
        handler.Hello();


    }
}
