package com.yao.spider.douban.task;

import com.yao.spider.douban.DoubanHttpClient;
import com.yao.spider.douban.dao.IMoveDao;
import com.yao.spider.douban.dao.Impl.MoveDaoImpl;
import com.yao.spider.douban.entity.move.Move;
import com.yao.spider.douban.parsers.DoubanPageParser;
import com.yao.spider.douban.parsers.DoubanParserFactory;
import com.yao.spider.douban.parsers.move.MoveDetailInfoParser;
import com.yao.spider.proxytool.ProxyPool;
import com.yao.spider.proxytool.entity.Page;
import com.yao.spider.proxytool.entity.Proxy;
import com.yao.spider.proxytool.http.util.HttpClientUtil;
import com.yao.spider.proxytool.proxyutil.ProxyUtil;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by 单耀 on 2018/1/28.
 * 电影详细信息下载任务
 */
public class DouBanDetailInfoDownLoadTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(DouBanInfoListPageTask.class);
    private Move move;
    private boolean isUseProxy;
    private Proxy currentProxy;
    private DoubanHttpClient doubanHttpClient = DoubanHttpClient.getInstance();
    private IMoveDao dao = new MoveDaoImpl();

    public DouBanDetailInfoDownLoadTask(Move move, boolean isUseProxy) {
        this.move = move;
        this.isUseProxy = isUseProxy;
    }

    public void run() {
        if (move!= null){
            //组装请求url，通过代理获取信息
            Page page = new Page();
            HttpGet request = new HttpGet(move.getUrl());
            try {
                if (isUseProxy) {
                    currentProxy = ProxyPool.proxyQueue.take();
                    HttpHost proxy = new HttpHost(currentProxy.getIp(), currentProxy.getPort());
                    request.setConfig(HttpClientUtil.getRequestConfigBuilder().setProxy(proxy).build());
                    page = doubanHttpClient.getPage(request);

                } else  {
                    page = doubanHttpClient.getPage(move.getUrl());
                }

                if (page!= null && page.getStatusCode() == 200) {
                    currentProxy.setSuccessfulTimes(currentProxy.getSuccessfulTimes() + 1);
                    handle(page);
                } else {
                    currentProxy.setFailureTimes(currentProxy.getFailureTimes() + 1);
                    retry();
                }
            } catch (Exception e) {
                currentProxy.setFailureTimes(currentProxy.getFailureTimes() + 1);
            } finally {
                if (request != null) {
                    request.releaseConnection();
                }
                if (currentProxy != null && !ProxyUtil.isDiscardProxy(currentProxy)) {
                    ProxyPool.proxyQueue.add(currentProxy);
                }
            }

        }
    }

    private void retry() {

    }

    private void handle(Page page) {
        DoubanPageParser parser = DoubanParserFactory.getDoubanParserFactory(MoveDetailInfoParser.class);
        List<Move> list = parser.parser(page.getHtml());
        if (list != null && list.size() > 0) {
            Move _move = list.get(0);
            _move.setId(move.getId());
            dao.update(_move);
//            _move.setName(move.getTitle());
//            _move.setId(move.getId());
//            _move.setScore(move.getRate());
            //保存到数据库
//            logger.info(_move.toString());
        }
    }
}
