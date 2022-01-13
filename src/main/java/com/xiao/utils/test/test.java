package com.xiao.utils.test;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import thredds.cataloggen.DatasetEnhancer;
import ucar.nc2.dataset.NetcdfDataset;

import java.lang.reflect.Method;
import java.util.*;

public class test {

    public static List<String> list = new ArrayList<String>();
    public static void main(String[] args) {

        test t = new test();
       list.add("1");
       list.add("2");
        list.sort(Comparator.<String>reverseOrder());
        System.out.println(list);


    }

    /**
     * @description 无限新增对象，堆溢出
     * @author Yxc
     * @date 2021/12/1 14:17
     * @param
     * @return void
     */
    public  void testHeap(){
        while (true){
            list.add("testHeapOOM");
        }
    }
    /**
     * @description 无限递归，类，栈溢出
     * @author Yxc
     * @date 2021/12/1 14:13
     * @param
     * @return void
     */
    public  void testStack(){
        this.testStack();
    }

    /**
     * @description 无限生成class，测试方法区内存溢出
     * @author Yxc
     * @date 2021/12/1 14:37
     * @param
     * @return void
     */
    public void testMethodAreaOOm() {
        while (true){
            //cglib
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(test.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, objects);
                }
            });
            //无限创建动态代理，生成class
            enhancer.create();
        }

    }
    /**
     * @description 测试内存泄露
     * @author Yxc
     * @date 2021/12/1 14:12
     * @param null
     * @return
     */
    static class Test1{
        public  Vector v = new Vector(10);
        public void  init(){
            for (int i = 0; i < 100 ; i++) {
                Object o = new Object();
                v.add(o);
                o = null;

            }
        }

    }
}
