package com.yao.test.test;

/**
 * Created by 单耀 on 2018/2/6.
 */
public class Main {
    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - s);
    }

}
