package com.baron.proxy;

import com.baron.data.ProxyWareHouse;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

/**
 * Created by baron on 17-5-27.
 */
public class AutoProxyPool implements ProxyProvider {
    private static final Logger LOG = Logger.getLogger(AutoProxyPool.class);
    private Queue<Proxy> queue;

    public AutoProxyPool() {
        init();
    }

    private void init() {
        queue = new ArrayDeque<>();
    }

    private Function<Map.Entry<AutoProxyPool, Collection<Proxy>>, Void> addProxies = entry -> {
        AutoProxyPool autoProxyPool = entry.getKey();
        Collection<Proxy> proxies = entry.getValue();

        if (proxies == null || proxies.isEmpty()) {
            return null;
        } // if

        synchronized (autoProxyPool) {
            for (Proxy proxy : proxies) {
                autoProxyPool.queue.offer(proxy);
            } // for
        } // synrhronized

        return null;
    };

    public Function<Map.Entry<AutoProxyPool, Collection<Proxy>>, Void> getAddProxies() {
        return addProxies;
    }


    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {
        if (page.getStatusCode() != 200 || page.getRawText() == null) {
            ProxyWareHouse.getProxyWarehouse().remove(proxy);
            remove(proxy);
        } // if
    }


    private synchronized void remove(Proxy proxy) {
        queue.remove(proxy);
    }

    @Override
    public synchronized Proxy getProxy(Task task) {
        Proxy proxy = queue.poll();
        queue.offer(proxy);
        return proxy;
    }
}