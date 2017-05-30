package com.baron.downloader;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * Created by Jason on 2017/5/29.
 */
public class CyclicDownloader extends HttpClientDownloader {
    private static final Logger LOG = Logger.getLogger(CyclicDownloader.class);

    @Override
    public Page download(Request request, Task task) {
        Site site = task.getSite();
        int retryTimes = site.getRetryTimes();
        int triedTime = 0;
        Page page;

        do {
            page = super.download(request, task);
            if (triedTime > 0) {
                try {
                    Thread.currentThread().sleep(site.getSleepTime());
                } catch (InterruptedException e) {
                    LOG.error(e);
                } // catch
            } // if
        } while (page == null && ++triedTime < retryTimes);

        return page;
    }
}
