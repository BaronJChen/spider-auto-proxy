package com.baron.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Jason on 2017/5/18.
 */
public class KuaiDaiLiPageProcessor implements PageProcessor {
    private Site site;

    public KuaiDaiLiPageProcessor() {
        site = Site.me()
                .setDomain("www.kuaidaili.com/proxylist/")
                .setCharset("utf-8")
                .setTimeOut(3 * 1000);
    }

    public void process(Page page) {
        Object o = page.getHtml().links().regex("(http://www\\.kuaidaili\\.com/proxylist/.*)").all();
        o = page.getHtml().links().regex("(/proxylist/.*)").all();
        // 处理页面的超链接
        page.addTargetRequests(page.getHtml().links().regex("(http://www\\.kuaidaili\\.com/proxylist/.*)").all());
        // 获取所需要的数据
        page.putField("proxy", page.getHtml().xpath("//table/tbody/tr/td/text()").all());
    }

    public Site getSite() {
        return site;
    }
}
