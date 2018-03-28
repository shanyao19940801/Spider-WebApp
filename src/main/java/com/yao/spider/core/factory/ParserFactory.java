package com.yao.spider.core.factory;

import com.yao.spider.douban.parsers.IPageParser;

/**
 * Created by 单耀 on 2018/1/30.
 */
public class ParserFactory {
    public static IPageParser getParserClass(Class clzz) {
        try {
            return (IPageParser) clzz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }
}
