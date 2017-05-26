package com.baron.spider.pipeline;

import com.baron.model.Proxy;
import com.baron.warehouse.ProxyWareHouse;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/5/18.
 */
public class KuaiDaiLiPipeLine implements Pipeline {
    private static final Logger LOG = Logger.getLogger(KuaiDaiLiPipeLine.class);

    public void process(ResultItems resultItems, Task task) {
        LOG.info("kuaidaili pipeline process task " + task.getUUID());

        List<String> proxyFields = resultItems.get("proxy");
        if (proxyFields.size() % 8 != 0) {
            return;
        } // if

        List<Proxy> proxies = new ArrayList<>();
        for (int i = 0; i < proxyFields.size(); ++i) {
            Proxy proxy = new Proxy();
            proxy.setIp(proxyFields.get(i));
            proxy.setPort(Integer.parseInt(proxyFields.get(i + 1)));
            proxy.setProtocolType(proxyFields.get(i + 3));

            proxies.add(proxy);
            i += 8;
        } // for

        ProxyWareHouse.getProxyWarehouse().putAll(proxies);
        System.out.println(String.format("there is %d proxies", ProxyWareHouse.getProxyWarehouse().count()));
    }
}
