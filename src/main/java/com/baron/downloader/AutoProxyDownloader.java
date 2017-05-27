package com.baron.downloader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * Created by baron on 17-5-27.
 */
public class AutoProxyDownloader extends HttpClientDownloader {
    @Override
    public Page download(Request request, Task task) {
        task.getSite().enableHttpProxyPool();
        return super.download(request, task);
    }
}
