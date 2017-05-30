package com.baron.spider;

import com.baron.data.ProxyWareHouse;
import com.baron.proxy.AutoProxyPool;
import com.baron.proxy.SimpleAutoProxyPool;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Jason on 2017/5/27.
 */
public class AutoProxySpiderBuilder {
   private AutoProxySpiderBuilder() {}

   public static Spider createAutoProxySpider(PageProcessor pageProcessor) {
       AutoProxyPool pool = new SimpleAutoProxyPool();
       ProxyWareHouse.getProxyWarehouse().registerProxyPool(pool, pool.getAddProxies());

       Site site = pageProcessor.getSite();
       site.enableHttpProxyPool();
       site.setHttpProxyPool(pool);

       return Spider.create(pageProcessor);
   }
}
