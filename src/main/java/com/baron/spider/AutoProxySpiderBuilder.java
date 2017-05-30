package com.baron.spider;

import com.baron.data.ProxyWareHouse;
import com.baron.proxy.AutoProxyPool;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Jason on 2017/5/27.
 */
public class AutoProxySpiderBuilder {

    static {
        ProxyAddressSpiderBuilder.createAllProxySpiders();
    }

    private AutoProxySpiderBuilder() {
    }

    public static Spider createAutoProxySpider(PageProcessor pageProcessor, HttpClientDownloader downloader) {
        AutoProxyPool pool = new AutoProxyPool();
        ProxyWareHouse.getProxyWarehouse().registerProxyPool(pool, pool.getAddProxies());
        downloader.setProxyProvider(pool);

        return Spider.create(pageProcessor).setDownloader(downloader);
    }
}
