package com.haohui.softwarecup.newsapi;

import com.haohui.softwarecup.newsapi.service.Recommend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NewsApiApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NewsApiApplication.class, args);
//        Recommend bean = context.getBean(Recommend.class);
//        bean.recommend();

    }
}
