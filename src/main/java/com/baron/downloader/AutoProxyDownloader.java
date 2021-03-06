package com.baron.downloader;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * Created by baron on 17-5-27.
 */
public class AutoProxyDownloader extends HttpClientDownloader {
    private static final Logger LOG = Logger.getLogger(AutoProxyDownloader.class);

    @Override
    public Page download(Request request, Task task) {
        return super.download(request, task);
    }
}
