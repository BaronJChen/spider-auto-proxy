package com.baron.sample;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Jason on 2017/5/30.
 */
public class GithubPageProcessor implements PageProcessor {
    private Site site;

    public GithubPageProcessor() {
        init();
    }

    private void init() {
        site = Site.me()
                .setDomain("http://www.github.com")
                .setCharset("utf-8")
                .setTimeOut(10 * 1000)
                .setRetryTimes(10)
                .setCycleRetryTimes(10)
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko)Chrome/57.0.2987.133 Safari/537.36");
    }

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().all());
        page.getResultItems().put("name", page.getHtml().xpath("//*[@id=\"js-repo-pjax-container\"]/div[1]/div[1]/h1/strong/a/text()").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
