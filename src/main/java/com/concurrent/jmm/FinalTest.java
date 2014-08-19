package com.concurrent.jmm;

/**
 * 实现描述: FinalTest
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-19 12:24
 */
public class FinalTest {
    int i;                       //普通变量
    final int j;                 //final变量
    static FinalTest obj;

    public FinalTest() {
        i = 1;
        j = 2;
    }

    public static void write() { //写线程A执行
        obj = new FinalTest();
    }

    public static void read() {  //读线程B执行
        FinalTest object = obj;  //读对象引用
        int a = object.i;        //读普通域
        int b = object.j;        //读final域
    }
}
