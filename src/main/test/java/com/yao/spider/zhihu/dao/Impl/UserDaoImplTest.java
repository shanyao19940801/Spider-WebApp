package com.yao.spider.zhihu.dao.Impl;

import com.yao.spider.zhihu.dao.IUserDao;
import com.yao.spider.zhihu.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shanyao on 2018/3/29.
 */
public class UserDaoImplTest {
    @Test
    public void inserSelective() throws Exception {
        User user = new User();
        user.setUserToken("001");
        IUserDao dao = new UserDaoImpl();
        dao.inserSelective(user);
    }

}