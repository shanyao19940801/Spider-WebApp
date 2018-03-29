package com.yao.spider.zhihu.parsers;

import com.yao.spider.core.parser.IPageParser;
import com.yao.spider.zhihu.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/3/28.
 */
public class ZhiHuUserParser implements IPageParser<User>{
    public List<User> parser(String html) {
        JSONObject object = JSONObject.fromObject(html);
        JSONArray jsonArray = object.getJSONArray("data");
//        jsonArray.toCollection(jsonArray, User.class);
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < jsonArray.size(); i++) {
            User user = new User();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer voteup_count = (Integer) jsonObject.get("voteup_count");
            user.setAgrees(voteup_count);
            Integer answer_count = (Integer) jsonObject.get("answer_count");
            user.setAnswers(answer_count);

            Integer question_count = (Integer) jsonObject.get("question_count");
            user.setAsks(question_count);
            String name = (String) jsonObject.get("name");
            user.setUsername(name);
            user.setBusiness("");//TODO
            JSONArray educations = jsonObject.getJSONArray("educations");
            if (educations != null && educations.size() > 1) {
                String school = (String)educations.getJSONObject(0).get("name");
                user.setEducation(school);
            }
            user.setEmployment("");//TODO
            Integer followwws = (Integer) jsonObject.get("following_count");
            user.setFollowees(followwws);
            Integer follower_count = (Integer) jsonObject.get("follower_count");
            user.setFollowers(follower_count);
            JSONArray locations = jsonObject.getJSONArray("locations");
            if (educations != null && educations.size() > 1) {
                String location = (String)locations.getJSONObject(0).get("name");
                user.setLocation(location);
            }

            user.setPosition("");//TODO
            Integer articles_count = (Integer) jsonObject.get("articles_count");
            user.setPosts(articles_count);
            user.setSex("");//TODO
            Integer thanked_count = (Integer) jsonObject.get("voteup_count");
            user.setThanks(thanked_count);
            String url = (String) jsonObject.get("url");
            user.setUrl(url);
            String url_token = (String) jsonObject.get("url_token");
            user.setUserToken(url_token);

            String id = (String) jsonObject.get("id");
            user.setUserId(id);
            userList.add(user);

        }
        Document document = Jsoup.parse(html);
        Elements elements = document.getAllElements();
        return null;
    }
}
