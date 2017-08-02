package com.yangge.domain;

/**
 * Created by yangge on 2017/8/2 0002.
 */
public class Page {
    private String pageName="";
    private String pageContent="未找到该章节";

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageContent() {
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }
}
