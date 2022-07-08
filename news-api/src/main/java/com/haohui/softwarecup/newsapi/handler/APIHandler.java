package com.haohui.softwarecup.newsapi.handler;

import com.haohui.softwarecup.newsapi.vo.ResultVO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

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
                "获取给定时间之后的num个新闻<br />"+
                "<br />"+
                "/api/news/{type}/{num}<br />"+
                "获取最多num条发布时间小于当前时间的对应类型的新闻"+
                "<br />"+
                "/api/newsType<br />"+
                "获取可选的新闻类型"
                ;
        return Mono.just(s);
    }
    @GetMapping("/api/newsType")
    public Mono<ResultVO> getNewsType(){
        return Mono.just(ResultVO.success(Map.of("newsType", NewsHandler.newsType)));
    }
}
