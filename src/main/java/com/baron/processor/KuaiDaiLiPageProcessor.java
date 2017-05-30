package com.baron.processor;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Jason on 2017/5/18.
 */
@Component
public class KuaiDaiLiPageProcessor implements PageProcessor {
    private static final Logger LOG = Logger.getLogger(KuaiDaiLiPageProcessor.class);
    private static final String KUAIDAILI_DOMAIN = "http://www.kuaidaili.com";
    private Site site;

    public KuaiDaiLiPageProcessor() {
        site = Site.me()
                .setDomain(KUAIDAILI_DOMAIN)
                .setCharset("utf-8")
                .setTimeOut(10 * 1000)
                .setRetryTimes(10)
                .setCycleRetryTimes(10)
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
    }

    public void process(Page page) {
        LOG.info("kuai dai li page processor process page " + page.getUrl());

        // 处理页面的超链接
        page.addTargetRequests(page.getHtml().links().regex("(http://www\\.kuaidaili\\.com/proxylist/[0-9]{1,2})").all());
        // 获取所需要的数据
        page.putField("proxy", page.getHtml().xpath("//table/tbody/tr/td/text()").all());
    }

    public Site getSite() {
        return site;
    }
}
