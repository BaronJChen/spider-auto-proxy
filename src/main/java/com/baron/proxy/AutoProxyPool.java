package com.baron.proxy;

import org.apache.http.HttpHost;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyPool;
import us.codecraft.webmagic.proxy.SimpleProxyPool;

/**
 * Created by baron on 17-5-27.
 */
// ProxyPool只是控制代理，包含启用，禁用，删除代理等等都是在这边实现的
public class AutoProxyPool implements ProxyPool {
    // 下载完成之后会调用这段代码，用于告知代理池是否可用
    @Override
    public void returnProxy(HttpHost host, int statusCode) {

    }

    @Override
    // 返回一个代理
    public Proxy getProxy() {
        return null;
    }

    @Override
    public boolean isEnable() {
        return true;
    }
}
