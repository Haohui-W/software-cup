package com.haohui.softwarecup.softwarecupapi.handler;

import com.haohui.softwarecup.softwarecupapi.dao.AppConfigDao;
import com.haohui.softwarecup.softwarecupapi.pojo.AppConfig;
import com.haohui.softwarecup.softwarecupapi.pojo.News;
import com.haohui.softwarecup.softwarecupapi.service.NewsService;
import com.haohui.softwarecup.softwarecupapi.service.UserService;
import com.haohui.softwarecup.softwarecupapi.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/news")
public class NewsHandler {

    private final NewsService newsService;
    private final UserService userService;


    private final Queue<News> newsQueue = new LinkedList<>();
    @Autowired
    public NewsHandler(NewsService newsService, UserService userService ) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping("/{uuid}")
    public Mono<ResultVO> freshNews(@PathVariable String uuid) {
        UUID uuid0=UUID.fromString(uuid);
        return userService.findById(uuid0)
                .map(e->{
                    if(e==null){
                        return Mono.just(ResultVO.reqErr("用户不存在"));
                    }else {
                        return newsService.recommend(uuid0)
                                .collectList()
                                .map(newsList-> ResultVO.ok(Map.of("newsList",e)));
                    }
                }).block();
    }


    @GetMapping("/push")
    public Mono<ResultVO> pushNews(){
        News news = newsQueue.poll();
        if(news==null) return Mono.just(ResultVO.reqErr("没有新的推送"));
        return Mono.just(ResultVO.ok(Map.of("news",news)));
    }

    @PostMapping("/push")
    public Mono<ResultVO> postPushNews(@RequestBody News news){
        news.setPublishedTime(LocalDateTime.now());
        return Mono.just(ResultVO.ok(Map.of("success",newsQueue.add(news))));
    }
}
