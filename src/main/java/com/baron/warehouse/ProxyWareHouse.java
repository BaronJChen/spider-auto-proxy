package com.baron.warehouse;

import com.baron.model.Proxy;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

/**
 * Created by Jason on 2017/5/19.
 */
public class ProxyWareHouse {
    private static ProxyWareHouse proxyWareHouse;

    static {
        proxyWareHouse = new ProxyWareHouse();
    }

    // 用于轮流使用代理
    private Queue<Proxy> queue;

    private ProxyWareHouse() {
        init();
    }

    private void init() {
        queue = new ArrayDeque<>();
    }

    public static ProxyWareHouse getProxyWarehouse() {
        return proxyWareHouse;
    }

    public synchronized Proxy get() throws InterruptedException {
        Proxy proxy = queue.poll();

        if (proxy != null) {
            queue.offer(proxy);
        }

        return proxy;
    }

    public synchronized boolean put(Proxy proxy) {
        if (queue.contains(proxy)) {
            return false;
        } // if

        return queue.offer(proxy);
    }

    public synchronized boolean putAll(Collection<Proxy> proxies) {
        boolean flag = false;

        for (Proxy proxy : proxies) {
            flag |= this.put(proxy);
        } // for

        return flag;
    }

    public int count() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
