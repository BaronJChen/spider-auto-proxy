package com.baron.proxy;

import com.baron.data.ProxyWareHouse;
import com.baron.model.Proxy;
import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.proxy.SimpleProxyPool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/**
 * Created by baron on 17-5-27.
 */
public class SimpleAutoProxyPool extends AutoProxyPool {
    private static final Logger LOG = Logger.getLogger(SimpleAutoProxyPool.class);

    private Function<Map.Entry<AutoProxyPool, Collection<Proxy>>, Void> addProxies = entry -> {
        AutoProxyPool autoProxyPool = entry.getKey();
        Collection<Proxy> baronProxies = entry.getValue();
        Map<String, us.codecraft.webmagic.proxy.Proxy> codecraftProxies = new HashMap<>();
        Class<us.codecraft.webmagic.proxy.Proxy> clazz = us.codecraft.webmagic.proxy.Proxy.class;

        // us.codecraft.webmagic.proxy.Proxy
        for (Proxy baronProxy : baronProxies) {
            try {
                us.codecraft.webmagic.proxy.Proxy codecraftProxy = clazz.newInstance();
                Field fieldHttpHost = clazz.getDeclaredField("httpHost");
                fieldHttpHost.setAccessible(true);
                fieldHttpHost.set(codecraftProxy, baronProxy.getHttpHost());

                codecraftProxies.put(UUID.randomUUID().toString(), codecraftProxy);
            } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                LOG.error(e);
            }
        } // for

        try {
            Method methodAddProxy = clazz.getDeclaredMethod("addProxy", Map.class);
            methodAddProxy.invoke(autoProxyPool, codecraftProxies);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOG.error(e);
        } // catch

        return null;
    };

    public Function<Map.Entry<AutoProxyPool, Collection<Proxy>>, Void> getAddProxies() {
        return addProxies;
    }

    @Override
    public void returnProxy(HttpHost host, int statusCode) {
        if (statusCode > 400) {
            ProxyWareHouse.getProxyWarehouse().removeByHttpHost(host);
        } // if

        super.returnProxy(host, statusCode);
    }
}