package com.yao.spider.zhihu.parsers;

import com.yao.spider.core.entity.Page;
import com.yao.spider.core.factory.ParserFactory;
import com.yao.spider.core.parser.IPageParser;
import com.yao.spider.zhihu.ZhiHuHttpClient;
import com.yao.spider.zhihu.config.ZhiHuConfig;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

/**
 * Created by user on 2018/3/28.
 */
public class UserParserTest {
    @Test
    public void parser() throws Exception {
        String au = "d66ce1d26d4e2ffe031d";
        String startToken = ZhiHuConfig.startUserToken;
        String startUrl = String.format(ZhiHuConfig.USER_FOLLOWEES_URL, startToken, 0);
        HttpGet request = new HttpGet(startUrl);
        request.setHeader("authorization","oauth " + au);
        Page page = ZhiHuHttpClient.getInstance().getPage(startUrl);
        IPageParser pageParser = ParserFactory.getParserClass(ZhiHuUserParser.class);
        pageParser.parser(page.getHtml());
    }

}