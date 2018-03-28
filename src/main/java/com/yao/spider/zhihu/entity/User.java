package com.yao.spider.zhihu.entity;

public class User {
    private String userToken;

    private String location;

    private String business;

    private String sex;

    private String employment;

    private String education;

    private String username;

    private String url;

    private Integer agrees;

    private Integer thanks;

    private Integer asks;

    private Integer answers;

    private Integer posts;

    private Integer followees;

    private Integer followers;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken == null ? null : userToken.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business == null ? null : business.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment == null ? null : employment.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getAgrees() {
        return agrees;
    }

    public void setAgrees(Integer agrees) {
        this.agrees = agrees;
    }

    public Integer getThanks() {
        return thanks;
    }

    public void setThanks(Integer thanks) {
        this.thanks = thanks;
    }

    public Integer getAsks() {
        return asks;
    }

    public void setAsks(Integer asks) {
        this.asks = asks;
    }

    public Integer getAnswers() {
        return answers;
    }

    public void setAnswers(Integer answers) {
        this.answers = answers;
    }

    public Integer getPosts() {
        return posts;
    }

    public void setPosts(Integer posts) {
        this.posts = posts;
    }

    public Integer getFollowees() {
        return followees;
    }

    public void setFollowees(Integer followees) {
        this.followees = followees;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    @Override
    public String toString() {
        return "User{" +
                "userToken='" + userToken + '\'' +
                ", location='" + location + '\'' +
                ", business='" + business + '\'' +
                ", sex='" + sex + '\'' +
                ", employment='" + employment + '\'' +
                ", education='" + education + '\'' +
                ", username='" + username + '\'' +
                ", url='" + url + '\'' +
                ", agrees=" + agrees +
                ", thanks=" + thanks +
                ", asks=" + asks +
                ", answers=" + answers +
                ", posts=" + posts +
                ", followees=" + followees +
                ", followers=" + followers +
                '}';
    }
}