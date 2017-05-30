package com.baron.pipeline;

import com.baron.data.ProxyWareHouse;
import com.baron.util.IpUtil;
import org.apache.http.annotation.ThreadSafe;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jason on 2017/5/18.
 */
@ThreadSafe
public class QuanWangDaiLiPipeLine extends DaiLiPipeLine {
    private static final Logger LOG = Logger.getLogger(QuanWangDaiLiPipeLine.class);

    public void process(ResultItems resultItems, Task task) {
        List<String> htmls = (List<String>) resultItems.get("ips");
        List<String> portStrings = (List<String>) resultItems.get("ports");
        int size = htmls.size();
        List<String> ips = new ArrayList<>(size);
        List<Integer> ports = new ArrayList<>(size);

        for (int i = 0; i < size; ++i) {
            String ip = getIpFromHtml(htmls.get(i));
            if (!IpUtil.validate(ip)) {
                continue;
            } // if

            ips.add(ip);
            ports.add(Integer.parseInt(portStrings.get(i)));
        } // for

        addToWareHouse(ips, ports);
    }

    private String getIpFromHtml(String html) {
        List<String> lines = new ArrayList<>(Arrays.asList(html.split("<")));
        String ip = "";

        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            String text = "";

            if (!((line.indexOf("display") != -1 && line.indexOf("none") != -1)
                    || line.indexOf(">:") != -1
                    || line.indexOf("port") != -1
                    || (text = line.substring(line.indexOf(">") + 1)).matches("\\s{0,}"))) {
                ip += text.trim();
            } // if
        } // for

        return ip;
    }

    @Override
    public void afterDone(List<Proxy> proxies) {
        ProxyWareHouse.getProxyWarehouse().putAll(proxies);
        System.out.println(String.format("quan wang spider there is %d proxies", ProxyWareHouse.getProxyWarehouse().count()));
    }
}
