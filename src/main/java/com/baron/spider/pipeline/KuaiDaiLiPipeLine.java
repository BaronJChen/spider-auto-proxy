package com.baron.spider.pipeline;

import com.baron.model.Proxy;
import com.baron.util.IpUtil;
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
        LOG.info("kuai daili pipeline process task " + task.getUUID());

        List<String> proxyFields = resultItems.get("proxy");
        if (proxyFields.size() % 8 != 0) {
            return;
        } // if

        List<Proxy> proxies = new ArrayList<>();
        for (int i = 0; i < proxyFields.size(); i += 8) {
            String ip = proxyFields.get(i);
            if (!IpUtil.validate(ip)) {
                continue;
            } // if

            Proxy proxy = new Proxy(ip, Integer.parseInt(proxyFields.get(i + 1))
                    , proxyFields.get(i + 3));

            proxies.add(proxy);
        } // for

        ProxyWareHouse.getProxyWarehouse().putAll(proxies);
        System.out.println(String.format("there is %d proxies", ProxyWareHouse.getProxyWarehouse().count()));
    }
}
