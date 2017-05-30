package com.baron.pipeline;

import com.baron.util.IpUtil;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.proxy.Proxy;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Jason on 2017/5/29.
 */
public abstract class DaiLiPipeLine implements Pipeline {
    private static final Logger LOG = Logger.getLogger(DaiLiPipeLine.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    protected void addToWareHouse(List<String> ips, List<Integer> ports) {
        final List<Proxy> proxies = new ArrayList<>();
        int size = ips.size();

        for (int i = 0; i < size; ++i) {
            proxies.add(new Proxy(ips.get(i), ports.get(i), null, null));
        } // for

        executorService.submit(() -> {
            afterDone(IpUtil.checkProxy(proxies));
        });
    }

    public abstract void afterDone(List<Proxy> proxies);
}
