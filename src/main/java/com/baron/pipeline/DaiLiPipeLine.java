package com.baron.pipeline;

import com.baron.model.Proxy;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jason on 2017/5/29.
 */
public abstract class DaiLiPipeLine implements Pipeline{
    private static final Logger LOG = Logger.getLogger(DaiLiPipeLine.class);
    private ThreadPoolExecutor executor;

    public DaiLiPipeLine() {
        init();
    }

    private void init() {
        executor = new ThreadPoolExecutor(2, 200, 100, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    }

    protected void addToWareHouse(List<String> ips, List<Integer> ports, List<String> protocolTypes) {
        new Thread(() -> {
            List<Proxy> proxies = new ArrayList<>();
            final int size = ips.size();
            final Object lock = new Object();

            for (int i = 0; i < size; ++i) {
                final int index = i;
                executor.submit(() -> {
                    try {
                        proxies.add(new Proxy(ips.get(index), ports.get(index), protocolTypes.get(index)));

                        if (proxies.size() == size) {
                            synchronized (lock) {
                                lock.notify();
                            } //synchronized
                        } // if
                    } catch (UnknownHostException e) {
                        LOG.error(e);
                    } // catch
                });
            } // for

            try {
                synchronized (lock) {
                    lock.wait();
                } //synchronized
                afterDone(proxies);
            } catch (InterruptedException e) {
                LOG.error(e);
            } // catch
        }).start();
    }

    public abstract void afterDone(List<Proxy> proxies);
}
