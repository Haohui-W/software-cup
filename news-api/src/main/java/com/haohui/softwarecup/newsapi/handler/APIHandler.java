package com.haohui.softwarecup.newsapi.handler;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
public class APIHandler {
    @GetMapping("/api")
    public Mono<String> getAPI(){
        String s= "/api<br />" +
                "获取接口帮助信息<br />" +
                "<br />" +
                "/api/news/{num}<br />" +
                "获取num个随机新闻<br />" +
                "<br />" +
                "/api/news/id/{id}<br />" +
                "根据id获取一个新闻<br />" +
                "<br />" +
                "/api/news/after/{year}/{mouth}/{day}/{num}<br />" +
                "获取给定时间之后的num个新闻";
        return Mono.just(s);
    }
}
