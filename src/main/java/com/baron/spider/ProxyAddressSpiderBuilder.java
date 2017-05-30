package com.baron.spider;

import com.baron.data.ProxyWareHouse;
import com.baron.downloader.AutoProxyDownloader;
import com.baron.downloader.CyclicDownloader;
import com.baron.pipeline.KuaiDaiLiPipeLine;
import com.baron.pipeline.QuanWangDaiLiPipeLine;
import com.baron.processor.KuaiDaiLiPageProcessor;
import com.baron.processor.QuanWangDaiLiPageProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jason on 2017/5/25.
 */
public class ProxyAddressSpiderBuilder {
    private static final String KUAI_DAI_LI_SEED = "http://www.kuaidaili.com/free/intr/";
    private static final String QUAN_WANG_DAI_LI_SEED = "http://www.goubanjia.com/free/index2.shtml";
    private static final int KUAI_DAI_LI_THREAD_NUM = 1;
    private static final int QUAN_WANG_DAI_LI_THREAD_NUM = 1;

    private ProxyAddressSpiderBuilder() {}

    public static Spider createKuaiDaiLiSpider() {
        return Spider.create(new KuaiDaiLiPageProcessor())
                .thread(KUAI_DAI_LI_THREAD_NUM)
                .setPipelines(Arrays.asList((Pipeline)new KuaiDaiLiPipeLine()))
                .addUrl(KUAI_DAI_LI_SEED)
                .setDownloader(new CyclicDownloader());
    }

    public static Spider createQuanWangDaiLiSpider() {
        return Spider.create(new QuanWangDaiLiPageProcessor())
                .thread(QUAN_WANG_DAI_LI_THREAD_NUM)
                .setPipelines(Arrays.asList((Pipeline)new QuanWangDaiLiPipeLine()))
                .addUrl(QUAN_WANG_DAI_LI_SEED)
                .setDownloader(new CyclicDownloader());
    }

    public static void createAllProxySpiders() {
        createKuaiDaiLiSpider().runAsync();
        createQuanWangDaiLiSpider().runAsync();
        while (ProxyWareHouse.getProxyWarehouse().count() < 10);
    }
}
