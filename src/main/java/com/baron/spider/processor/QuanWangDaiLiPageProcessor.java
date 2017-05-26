package com.baron.spider.processor;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Jason on 2017/5/18.
 */
public class QuanWangDaiLiPageProcessor implements PageProcessor {
    private static final Logger LOG = Logger.getLogger(QuanWangDaiLiPageProcessor.class);
    private static final String QUANWANGDAILI_DOMAIN = "http://www.goubanjia.com/free";
    private Site site;

    public QuanWangDaiLiPageProcessor() {
        site = Site.me()
                .setDomain(QUANWANGDAILI_DOMAIN)
                .setCharset("utf-8")
                .setTimeOut(3 * 1000)
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
    }

    public void process(Page page) {
        LOG.info("quan wang dai li page processor process page " + page.getRequest().getUrl());

        // 处理页面的超链接
        page.addTargetRequests(page.getHtml().links().regex("http://www.goubanjia.com/free/index[0-9]{0,2}\\.shtml").all());
        // 获取所需要的数据
        page.putField("ips", page.getHtml().xpath("//td[@class='ip']/html()").all());
        page.putField("ports", page.getHtml().xpath("//span[@class='port']/text()").all());
        page.putField("protocolTypes", page.getHtml().xpath("//*[@id=\"list\"]/table/tbody/tr/td[3]/a/text()").all());
    }

    public Site getSite() {
        return site;
    }
}
