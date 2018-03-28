package com.yao.spider.zhihu.task;

import com.yao.spider.core.entity.Page;
import com.yao.spider.core.factory.ParserFactory;
import com.yao.spider.core.http.util.HttpClientUtil;
import com.yao.spider.core.parser.IPageParser;
import com.yao.spider.core.util.ProxyUtil;
import com.yao.spider.proxytool.ProxyPool;
import com.yao.spider.proxytool.entity.Proxy;
import com.yao.spider.zhihu.ZhiHuHttpClient;
import com.yao.spider.zhihu.entity.User;
import com.yao.spider.zhihu.parsers.ZhiHuUserParser;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by user on 2018/3/28.
 */
public class DownUserListTask implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(DownUserListTask.class);
    private String url;
    private Proxy proxy;
    private boolean ebableProxy;

    public DownUserListTask(String url,boolean enableProxy) {
        this.url = url;
        this.ebableProxy = enableProxy;
    }

    public void run() {
        HttpGet request = new HttpGet(url);
        Page page = null;
        try {
            if (ebableProxy) {
                proxy = ProxyPool.proxyQueue.take();
                HttpHost host = new HttpHost(this.proxy.getIp(),this.proxy.getPort());
                request.setConfig(HttpClientUtil.getRequestConfigBuilder().setProxy(host).build());
                page = ZhiHuHttpClient.getInstance().getPage(request);
            } else {
                page = ZhiHuHttpClient.getInstance().getPage(this.url);
            }
            if (page != null && page.getStatusCode() == 200) {
                handPage(page);
            } else {
                this.proxy.setFailureTimes(proxy.getFailureTimes() + 1);
                retry();
            }
        } catch (Exception e) {
            proxy.setFailureTimes(proxy.getFailureTimes() + 1);
            retry();
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
            if (proxy != null && !ProxyUtil.isDiscardProxy(proxy)) {
                ProxyPool.proxyQueue.add(proxy);
            }
        }
    }

    public void handPage(Page page) {
        IPageParser pageParser = ParserFactory.getParserClass(ZhiHuUserParser.class);
        List<User> list = pageParser.parser(page.getHtml());
        if (list != null && list.size() > 0) {
            for (User user : list) {
                logger.info(user.toString());
            }
        }
    }

    public void retry() {
        ZhiHuHttpClient.getInstance().getUserListDownTask().execute(new DownUserListTask(this.url, true));
    }
}
