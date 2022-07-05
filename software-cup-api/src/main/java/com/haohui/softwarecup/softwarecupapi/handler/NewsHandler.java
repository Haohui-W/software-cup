package com.haohui.softwarecup.softwarecupapi.handler;

import com.haohui.softwarecup.softwarecupapi.dao.UserDao;
import com.haohui.softwarecup.softwarecupapi.service.NewsService;
import com.haohui.softwarecup.softwarecupapi.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/news")
public class NewsHandler {

    private final NewsService newsService;
    private final UserDao userDao;
    @Autowired
    public NewsHandler(NewsService newsService, UserDao userDao) {
        this.newsService = newsService;
        this.userDao = userDao;
    }

    @GetMapping("/{uuid}")
    public Mono<ResultVO> freshNews(@PathVariable String uuid) {
        UUID uuid0=UUID.fromString(uuid);
        return userDao.findById(uuid0)
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
}
