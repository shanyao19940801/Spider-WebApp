package com.yao.spider;

import com.yao.spider.common.config.CommonConfig;
import com.yao.spider.douban.DoubanHttpClient;
import com.yao.spider.proxytool.ProxyHttpClient;
import com.yao.spider.zhihu.ZhiHuHttpClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by shanyao on 2018/3/29.
 */
public class AutoStart implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ProxyHttpClient.getInstance().startProxy();
//        DoubanHttpClient.getInstance().startDouBan();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (CommonConfig.FUTURE_DOUBAN) {
            DoubanHttpClient.getInstance().startDouBan();
        }

        if (CommonConfig.FUTURE_ZHIHU) {
            ZhiHuHttpClient.getInstance().startZhiHu();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
