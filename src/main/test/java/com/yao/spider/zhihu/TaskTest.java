package com.yao.spider.zhihu;

import com.yao.spider.common.task.GetProxyTask;
import com.yao.spider.zhihu.task.ZhiHuUserListTask;
import org.junit.Test;

/**
 * Created by shanyao on 2018/3/29.
 */
public class TaskTest {
    public static void main(String[] args) {
        new Thread(new GetProxyTask()).start();
       /* while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("1");
        }*/
    }
    /*@Test
    public void test(){
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       *//* while (true) {
            try {
                Thread.sleep(10000);
                System.out.println("1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*//*
        new Thread(new GetProxyTask()).start();
    }*/
}
