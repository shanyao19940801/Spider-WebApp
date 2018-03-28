package com.yao.spider.zhihu;

import com.yao.spider.core.http.client.AbstractHttpClient;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2018/3/28.
 */
public class ZhiHuHttpClient extends AbstractHttpClient{
    private static ZhiHuHttpClient zhiHuHttpClient;

    public ThreadPoolExecutor userListDownTask;

    public static ZhiHuHttpClient getInstance() {
        if (zhiHuHttpClient == null) {
            synchronized (ZhiHuHttpClient.class) {
                if (zhiHuHttpClient == null) {
                    zhiHuHttpClient = new ZhiHuHttpClient();
                }
            }
        }
        return zhiHuHttpClient;
    }

    public ZhiHuHttpClient() {
        init();
    }

    private void init() {
        userListDownTask = new ThreadPoolExecutor(100, 100, 0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        return new Thread(r,"userListDownTask" + r.hashCode());
                    }
                });
    }

    public void startZhiHu() {
        new Thread(new Runnable() {
            public void run() {

            }
        }).start();
    }

    public ThreadPoolExecutor getUserListDownTask() {
        return userListDownTask;
    }
}
