import com.baron.spider.ProxySpiderBuilder;
import com.baron.spider.pipeline.QuanWangDaiLiPipeLine;
import com.baron.spider.processor.QuanWangDaiLiPageProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Arrays;

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
    public CommandLineRunner startKuaiDaLiSpider() {
        return new CommandLineRunner() {
            public void run(String... strings) throws Exception {
                Spider spider = ProxySpiderBuilder.createQuanWangDaiLiSpider(new QuanWangDaiLiPageProcessor(), Arrays.asList((Pipeline) new QuanWangDaiLiPipeLine()));
                spider.runAsync();
            }
        };
    }
}
