package com.baron.spider.pipeline;

import com.baron.model.Proxy;
import com.baron.util.IpUtil;
import com.baron.warehouse.ProxyWareHouse;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jason on 2017/5/18.
 */
public class QuanWangDaiLiPipeLine implements Pipeline {
    private static final Logger LOG = Logger.getLogger(QuanWangDaiLiPipeLine.class);

    public void process(ResultItems resultItems, Task task) {
        LOG.info("quanwang daili pipeline process task " + task.getUUID());

        List<String> htmls = (List<String>) resultItems.get("ips");
        List<String> ports = (List<String>) resultItems.get("ports");
        List<String> protocolTypes = (List<String>) resultItems.get("protocolTypes");
        List<Proxy> proxies = new ArrayList<>();

        for (int i = 0; i < htmls.size(); ++i) {
            String ip = getIpFromHtml(htmls.get(i));
            if (!IpUtil.validate(ip)) {
                continue;
            } // if

            Proxy proxy = new Proxy(ip, Integer.parseInt(ports.get(i))
                    , protocolTypes.get(i));

            proxies.add(proxy);
        } // for

        ProxyWareHouse.getProxyWarehouse().putAll(proxies);
        System.out.println(String.format("there is %d proxies", ProxyWareHouse.getProxyWarehouse().count()));
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
}
