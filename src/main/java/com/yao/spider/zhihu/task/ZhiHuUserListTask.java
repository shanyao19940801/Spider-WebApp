package com.yao.spider.zhihu.task;

import com.yao.spider.common.config.CommonConfig;
import com.yao.spider.core.entity.Page;
import com.yao.spider.core.factory.ParserFactory;
import com.yao.spider.core.http.util.HttpClientUtil;
import com.yao.spider.core.parser.IPageParser;
import com.yao.spider.core.util.ProxyUtil;
import com.yao.spider.proxytool.ProxyPool;
import com.yao.spider.proxytool.entity.Proxy;
import com.yao.spider.zhihu.ZhiHuHttpClient;
import com.yao.spider.zhihu.config.ZhiHuConfig;
import com.yao.spider.zhihu.dao.IUserDao;
import com.yao.spider.zhihu.dao.Impl.UserDaoImpl;
import com.yao.spider.zhihu.entity.User;
import com.yao.spider.zhihu.parsers.ZhiHuUserParser;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by user on 2018/3/28.
 */
public class ZhiHuUserListTask implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ZhiHuUserListTask.class);
    private String url;
    private Proxy proxy;
    private boolean ebableProxy;
    private HttpRequestBase request;
    private String userToken;
    //同一个token重试次数记录，如果超过五次就放弃不在重试
    private int retryTimes;


    public ZhiHuUserListTask(String url, boolean enableProxy) {
        this.url = url;
        this.ebableProxy = enableProxy;
    }
    public ZhiHuUserListTask(String url, boolean enableProxy, String userToken) {
        this.url = url;
        this.ebableProxy = enableProxy;
        this.userToken = userToken;
    }

    public ZhiHuUserListTask(String url, boolean enableProxy, String userToken, int retryTimes) {
        this.url = url;
        this.ebableProxy = enableProxy;
        this.userToken = userToken;
        this.retryTimes = retryTimes;
    }


    public void run() {
        HttpGet request = new HttpGet(url);
        request.setHeader("authorization","oauth " + ZhiHuConfig.authorization);
        Page page = null;
        try {
            if (ebableProxy) {
                logger.info("当前可用代理:" + ProxyPool.proxyQueue.size());
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
            if (CommonConfig.dbEnable) {
                IUserDao dao = new UserDaoImpl();
                for (User user : list) {
                    dao.inserSelective(user);
                }
            }
            for (User user : list) {
                //TODO 先查询usertoken是否已经爬过
                if ( !ZhiHuHttpClient.getInstance().getUserListDownTask().isShutdown()) {
                    for (int i = 0; i < user.getFollowees() / 20; i++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (ZhiHuHttpClient.getInstance().getUserListDownTask().getQueue().size() > 888) {
//                            logger.info("线程数量较多:"+ ZhiHuHttpClient.getInstance().getUserListDownTask().getQueue().size());
                            continue;
                        }
                        logger.info("当前活跃线程数：" + ZhiHuHttpClient.getInstance().getUserListDownTask().getActiveCount());
                        String nextUrl = String.format(ZhiHuConfig.FOLLOWEES_API, user.getUserToken(), i * 20);
                        ZhiHuHttpClient.getInstance().getUserListDownTask().execute(new ZhiHuUserListTask(nextUrl, true, user.getUserToken()));
                    }
                }
                // TODO 将usertoken保存到数据库，避免大量重复查询

            }

        }
    }

    public void retry() {
        logger.info("当前活跃线程数：" + ZhiHuHttpClient.getInstance().getUserListDownTask().getActiveCount());
        logger.info("重试" + this.userToken + "---重试次数：" + retryTimes + "---代理：" + proxy.getProxyStr());
        if (retryTimes < 5 || ZhiHuConfig.startUserToken.equals(this.userToken)) {
            ZhiHuHttpClient.getInstance().getUserListDownTask().execute(new ZhiHuUserListTask(this.url, true, this.userToken, this.retryTimes + 1));
        }
    }
}
