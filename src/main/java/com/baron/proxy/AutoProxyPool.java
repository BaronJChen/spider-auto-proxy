package com.baron.proxy;

import com.baron.model.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyPool;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Jason on 2017/5/30.
 */
public abstract class AutoProxyPool extends SimpleProxyPool {

    public abstract Function<Map.Entry<AutoProxyPool, Collection<Proxy>>, Void> getAddProxies();
}
