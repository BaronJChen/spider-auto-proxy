import com.baron.spider.ProxySpiders;
import com.baron.spider.pipeline.KuaiDaiLiPipeLine;
import com.baron.spider.processor.KuaiDaiLiPageProcessor;
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
                Spider spider = ProxySpiders.createKuaiDaiLiSpider(new KuaiDaiLiPageProcessor(), Arrays.asList((Pipeline) new KuaiDaiLiPipeLine()));
                spider.runAsync();
            }
        };
    }
}
