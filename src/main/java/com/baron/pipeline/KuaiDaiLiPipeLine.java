package com.baron.pipeline;

import com.baron.model.Proxy;
import com.baron.util.IpUtil;
import com.baron.data.ProxyWareHouse;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/5/18.
 */
public class KuaiDaiLiPipeLine extends DaiLiPipeLine {
    private static final Logger LOG = Logger.getLogger(KuaiDaiLiPipeLine.class);

    public void process(ResultItems resultItems, Task task) {
        LOG.info("kuai daili pipeline process task " + task.getUUID());

        List<String> proxyFields = resultItems.get("proxy");
        if (proxyFields.size() % 8 != 0) {
            return;
        } // if

        int size = proxyFields.size() / 8;
        List<String> ips = new ArrayList<>(size);
        List<Integer> ports = new ArrayList<>(size);
        List<String> protocolTypes = new ArrayList<>(size);

        for (int i = 0; i < size; ++i) {
            String ip = proxyFields.get(8 * i);
            if (!IpUtil.validate(ip)) {
                LOG.error("wrong ip: " + ip);
                continue;
            } // if

            ips.add(ip);
            ports.add(Integer.parseInt(proxyFields.get(8 * i + 1)));
            protocolTypes.add(proxyFields.get(8 * i  + 3).toLowerCase());
        } // for

        addToWareHouse(ips, ports, protocolTypes);
    }

    @Override
    public void afterDone(List<Proxy> proxies) {
        ProxyWareHouse.getProxyWarehouse().putAll(proxies);
        System.out.println(String.format("kuai dai li spider there is %d proxies", ProxyWareHouse.getProxyWarehouse().count()));
    }
}
