package com.yao.test.spider.zhihu.dao.Impl;

import com.yao.spider.zhihu.dao.IUserTokenDao;
import com.yao.spider.zhihu.dao.Impl.UserTokenDaoImpl;
import com.yao.spider.zhihu.entity.UserToken;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shanyao on 2018/4/3.
 */
public class UserTokenDaoImplTest {
    @Test
    public void insertSelective() throws Exception {
        IUserTokenDao userTokenDao = new UserTokenDaoImpl();
        userTokenDao.insertSelective(new UserToken("asdf"));
    }

    @Test
    public void selectByPrimaryKey() throws Exception {
        IUserTokenDao userTokenDao = new UserTokenDaoImpl();
        UserToken userToken = userTokenDao.selectByPrimaryKey("123");
        System.out.println(userToken.getUserToken());
    }

    @Test
    public void judgeAndInsert() throws Exception {
        IUserTokenDao userTokenDao = new UserTokenDaoImpl();
        System.out.println(userTokenDao.judgeAndInsert(new UserToken("asd")));
    }

}