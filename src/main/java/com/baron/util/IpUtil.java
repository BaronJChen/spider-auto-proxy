package com.baron.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.annotation.ThreadSafe;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by baron on 17-5-26.
 */
@ThreadSafe
public class IpUtil {
    private static final String REQUEST_URL = "https://www.baidu.com";
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(200, 2000, 10, TimeUnit.MINUTES
            , new LinkedBlockingDeque<Runnable>());
    private IpUtil() {}

    public static boolean validate(String ipString) {
        // use regex
        // ip address should be like this, 12.12.12.12
        return ipString.matches("(\\d{1,3}.){3}\\d{1,3}");
    }

    public static List<Proxy> checkProxy(Collection<Proxy> proxies) {
        AtomicInteger count = new AtomicInteger(0);
        List<Proxy> usableProxies = new ArrayList<>();
        int size = proxies.size();

        for (Proxy proxy : proxies) {
            final String proxyHost = proxy.getHost();
            final int proxyPort = proxy.getPort();

            executor.submit(() -> {
                try {
                    HttpClient httpClient = new HttpClient();
                    httpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);

                    int connectionTimeout = 3000;
                    int soTimeout = 3000;

                    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
                    httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

                    HttpMethod method = new GetMethod(REQUEST_URL);
                    int statusCode = httpClient.executeMethod(method);
                    if (statusCode == 200) {
                        usableProxies.add(proxy);
                    } // if
                } catch (Throwable e) {
                    System.out.println("useless ip " + proxyHost + ", msg: " + e.getMessage());
                } finally {
                    count.addAndGet(1);
                }
            });
        }

        while (count.get() < size) {
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return usableProxies;
    }
}
