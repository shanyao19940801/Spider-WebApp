package com.yao.spider.zhihu;

import com.yao.spider.core.entity.Page;
import com.yao.spider.core.http.client.AbstractHttpClient;
import com.yao.spider.zhihu.config.ZhiHuConfig;
import com.yao.spider.zhihu.task.GetAuthorizationTask;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2018/3/28.
 */
public class ZhiHuHttpClient extends AbstractHttpClient{
    private static final Logger logger = LoggerFactory.getLogger(ZhiHuHttpClient.class);
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
        String authorization = ZhiHuConfig.authorization;
        String startToken = ZhiHuConfig.startUserToken;
        String startUrl = String.format(ZhiHuConfig.FOLLOWEES_API, startToken, 0);
        HttpGet request = new HttpGet(startUrl);
        request.setHeader("authorization","oauth " + authorization);
    }


    public ThreadPoolExecutor getUserListDownTask() {
        return userListDownTask;
    }
}
