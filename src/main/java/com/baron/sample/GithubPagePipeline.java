package com.baron.sample;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by Jason on 2017/5/30.
 */
public class GithubPagePipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println((List)resultItems.get("name"));
    }
}
