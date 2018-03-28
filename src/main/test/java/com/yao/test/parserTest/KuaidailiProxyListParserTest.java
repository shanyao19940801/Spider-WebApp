package com.yao.test.parserTest;

import com.yao.spider.core.entity.Page;
import com.yao.spider.core.factory.ParserFactory;
import com.yao.spider.douban.DoubanHttpClient;
import com.yao.spider.douban.parsers.IPageParser;
import com.yao.spider.proxytool.parses.kuaidaili.KuaidailiProxyListParser;
import org.junit.Test;

/**
 * Created by user on 2018/3/28.
 */
public class KuaidailiProxyListParserTest{
    @Test
    public void parser() throws Exception {
        String url = "https://www.kuaidaili.com/free/inha/200/";
        IPageParser parser = ParserFactory.getParserClass(KuaidailiProxyListParser.class);
        Page page = DoubanHttpClient.getInstance().getPage(url);
        parser.parser(page.getHtml());
    }

}