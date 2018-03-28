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
        String authorization = initAuthorization();
        logger.info("成功获取authorization！");
        String startToken = ZhiHuConfig.startUserToken;
        String startUrl = String.format(ZhiHuConfig.FOLLOWEES_API, startToken, 0);
        HttpGet request = new HttpGet(startUrl);
        request.setHeader("authorization","oauth " + authorization);
    }

    /**
     * 初始化 authorization
     * @return
     */
    public String initAuthorization() {
        String content = null;
        GetAuthorizationTask getAuthorizationTask = new GetAuthorizationTask(ZhiHuConfig.startURL, false);
        getAuthorizationTask.run();
        content = getAuthorizationTask.getPage().getHtml();
        //获取oauth
        Pattern pattern = Pattern.compile("https://static\\.zhihu\\.com/heifetz/main\\.app\\.([0-9]|[a-z])*\\.js");
        Matcher matcher = pattern.matcher(content);
        String jsSrc = null;
        if (matcher.find()) {
            jsSrc = matcher.group(0);
        } else {
            throw new RuntimeException("not find javascript url");
        }
        String jsContent = null;
        GetAuthorizationTask jsPageTask = new GetAuthorizationTask(jsSrc, false);
        jsPageTask.run();
        jsContent = jsPageTask.getPage().getHtml();

        pattern = Pattern.compile("oauth\\\"\\),h=\\\"(([0-9]|[a-z])*)\"");
        matcher = pattern.matcher(jsContent);
        if (matcher.find()) {
            String authorization = matcher.group(1);
            logger.info("初始化authorization完成");
            return authorization;
        }
        throw new RuntimeException("not get authorization");

    }

    public ThreadPoolExecutor getUserListDownTask() {
        return userListDownTask;
    }
}
