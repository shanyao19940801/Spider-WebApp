package com.yao.spider;

import com.yao.spider.common.config.CommonConfig;
import com.yao.spider.common.task.GetProxyTask;
import com.yao.spider.core.constants.ProxyConstants;
import com.yao.spider.douban.DoubanHttpClient;
import com.yao.spider.proxytool.ProxyHttpClient;
import com.yao.spider.zhihu.ZhiHuHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by shanyao on 2018/3/29.
 */
public class AutoStart implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(AutoStart.class);
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (ProxyConstants.ISUSERFILE_ONLY) {
            ProxyHttpClient.getInstance().startProxy();
        }
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

        new Thread(new GetProxyTask()).start();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
