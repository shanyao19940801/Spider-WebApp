package com.yao.spider.zhihu.task;

import com.yao.spider.core.entity.Page;
import com.yao.spider.core.http.util.HttpClientUtil;
import com.yao.spider.proxytool.ProxyPool;
import com.yao.spider.proxytool.entity.Proxy;
import com.yao.spider.zhihu.ZhiHuHttpClient;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * Created by shanyao on 2018/3/28.
 */
public class GetAuthorizationTask implements Runnable{
    public static Page page;
    private String url;
    boolean isProxy;
    public GetAuthorizationTask() {
    }

    public GetAuthorizationTask(String url,boolean isProxy) {
        this.url = url;
        this.isProxy = isProxy;
    }

    public void run() {

        HttpGet request = null;
        try {
            if (isProxy) {
                request = new HttpGet(url);
                Proxy p = ProxyPool.proxyQueue.take();
                HttpHost proxy = new HttpHost(p.getIp(), p.getPort());
                request.setConfig(HttpClientUtil.getRequestConfigBuilder().setProxy(proxy).build());
                page = ZhiHuHttpClient.getInstance().getPage(request);
            } else {
                page = ZhiHuHttpClient.getInstance().getPage(url);
            }
            if (page == null || page.getStatusCode() != 200) {
                retry();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }

    }

    private void retry() {
        try {
            Thread.sleep(2000);
            this.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Page getPage() {
        return page;
    }
}
