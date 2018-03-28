package com.yao.spider.zhihu.parsers;

import com.yao.spider.core.parser.IPageParser;
import com.yao.spider.zhihu.entity.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by user on 2018/3/28.
 */
public class ZhiHuUserParser implements IPageParser<User>{
    public List<User> parser(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.getAllElements();
        return null;
    }
}
