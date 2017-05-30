package com.baron.data;

import com.baron.proxy.AutoProxyPool;
import com.baron.util.IpUtil;
import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.apache.http.annotation.ThreadSafe;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.*;
import java.util.function.Function;

/**
 * Created by Jason on 2017/5/19.
 */
@ThreadSafe
public class ProxyWareHouse {
    private static final Logger LOG = Logger.getLogger(ProxyWareHouse.class);
    private static final ProxyWareHouse proxyWareHouse = new ProxyWareHouse();
    private static final int PROXY_NUM_LIMIT = 10;

    // 用于轮流使用代理
    private Queue<Proxy> queue;
    private Map<AutoProxyPool, Function<Map.Entry<AutoProxyPool, Collection<Proxy>>, Void>> autoProxyPoolFunctionMap;
    private Map<AutoProxyPool, Collection<Proxy>> autoProxyPoolSetMap;

    private ProxyWareHouse() {
        init();
    }

    private void init() {
        queue = new ArrayDeque<>();
        autoProxyPoolFunctionMap = new HashMap<>();
        autoProxyPoolSetMap = new HashMap<>();
    }

    public static ProxyWareHouse getProxyWarehouse() {
        return proxyWareHouse;
    }

    public synchronized Proxy get() throws InterruptedException {
        Proxy proxy = queue.poll();

        if (proxy != null) {
            queue.offer(proxy);
        } // if

        return proxy;
    }

    public synchronized Proxy getUninterruptly() {
        Proxy proxy = null;

        do {
            try {
                proxy = get();
            } catch (InterruptedException e) {
                LOG.error(e);
            } // catch
        } while (proxy == null);

        return proxy;
    }

    public synchronized boolean put(Proxy proxy) {
        if (proxy == null || queue.contains(proxy)) {
            return false;
        } // if

        for (Map.Entry<AutoProxyPool, Collection<Proxy>> entry : autoProxyPoolSetMap.entrySet()) {
            AutoProxyPool pool = entry.getKey();
            Collection<Proxy> proxies = entry.getValue();

            if (proxies.size() + 1 >= PROXY_NUM_LIMIT) {
                autoProxyPoolFunctionMap.get(pool).apply(entry);
                entry.setValue(new HashSet<>());
            } else {
                proxies.add(proxy);
            } // else
        } // for

        return queue.offer(proxy);
    }

    public synchronized boolean putAll(Collection<Proxy> proxies) {
        if (proxies == null || proxies.isEmpty()) {
            return false;
        } // if

        boolean flag = false;

        for (Proxy proxy : proxies) {
            flag |= this.put(proxy);
        } // for

        IpUtil.checkProxy(proxies);
        return flag;
    }

    // TODO: to be optimized
    public synchronized void registerProxyPool(AutoProxyPool pool, Function<Map.Entry<AutoProxyPool, Collection<Proxy>>, Void> function) {
        autoProxyPoolFunctionMap.put(pool, function);
        autoProxyPoolSetMap.put(pool, new HashSet<>(queue));

        function.apply(new DefaultMapEntry(pool, queue));
    }

    public synchronized void remove(Proxy proxy) {
        if (proxy == null) {
            return;
        } // if

        queue.remove(proxy);
    }

    public int count() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
