package com.yao.spider.zhihu;

import com.yao.spider.zhihu.task.ZhiHuUserListTask;
import org.junit.Test;

/**
 * Created by shanyao on 2018/3/29.
 */
public class TaskTest {
    @Test
    public void test(){
        new ZhiHuUserListTask("",false,"").run();
    }
}
