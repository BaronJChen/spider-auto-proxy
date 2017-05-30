package com.baron.sample;

import com.baron.downloader.CyclicDownloader;
import com.baron.spider.AutoProxySpiderBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * Created by Jason on 2017/5/18.
 */
@SpringBootApplication
@ComponentScan("com.baron")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner startAutoProxySpider() {
        return new CommandLineRunner() {
            public void run(String... strings) throws Exception {
                Spider spider = AutoProxySpiderBuilder.createAutoProxySpider(new GithubPageProcessor(), new CyclicDownloader());
                spider.addUrl("https://github.com/gsh199449");
                //spider.run();
            }
        };
    }
}
