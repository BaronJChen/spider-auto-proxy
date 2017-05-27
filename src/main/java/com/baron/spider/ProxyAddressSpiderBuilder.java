package com.baron.spider;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by Jason on 2017/5/25.
 */
public class ProxyAddressSpiderBuilder {
    private static final String KUAI_DAI_LI_SEED = "http://www.kuaidaili.com";
    private static final String QUAN_WANG_DAI_LI_SEED = "http://www.goubanjia.com/free/index2.shtml";

    private ProxyAddressSpiderBuilder() {}

    public static Spider createKuaiDaiLiSpider(PageProcessor pageProcessor, List<Pipeline> pipelines) {
        return Spider.create(pageProcessor)
                .thread(2)
                .setPipelines(pipelines)
                .addUrl(KUAI_DAI_LI_SEED);
    }

    public static Spider createQuanWangDaiLiSpider(PageProcessor pageProcessor, List<Pipeline> pipelines) {
        return Spider.create(pageProcessor)
                .thread(2)
                .setPipelines(pipelines)
                .addUrl(QUAN_WANG_DAI_LI_SEED);
    }
}
