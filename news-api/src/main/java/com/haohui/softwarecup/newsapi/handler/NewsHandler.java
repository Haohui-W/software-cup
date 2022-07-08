package com.haohui.softwarecup.newsapi.handler;

import com.haohui.softwarecup.newsapi.dao.NewsDao;
import com.haohui.softwarecup.newsapi.pojo.News;
import com.haohui.softwarecup.newsapi.vo.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@CrossOrigin
public class NewsHandler {
    private static final List<String> newsType = List.of("体育", "娱乐", "家居", "彩票", "房产", "教育", "时尚", "时政", "星座", "游戏", "社会", "科技", "股票", "财经");

    private final NewsDao newsDao;

    public NewsHandler(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    /**
     * 根据id获取单个新闻
     * @param id id
     * @return resultVO
     */
    @GetMapping("/id/{id}")
    public Mono<ResultVO> getNews(@PathVariable long id){
        return newsDao.getNewsByNid(id)
                .map(e -> ResultVO.success(Map.of("news", e)));
    }

    @Value("${app.newsNum}")
    private int newsNum;

    /**
     * 随机获取num个新闻
     * num必须不大于100
     * @param num 数量
     * @return resultVO
     */
    @GetMapping("/{num}")
    public Mono<ResultVO> getNRandomNews(@PathVariable long num){
        if (num>100){
            return Mono.just(ResultVO.error(400,"一次性获取的数量不能大于100"));
        }
        return newsDao.getNRandomNews(LocalDateTime.now(),num)
                .collectList()
                .map(
                        e -> ResultVO.success(
                                Map.of("news", e)
                        )
                );
    }

    /**
     * 根据日期获取num个大于日期的新闻
     * 日期无效时返回错误
     * 数量大于100也错误
     * @param year 年
     * @param mouth 月
     * @param day 日
     * @param num 数量
     * @return resultVO
     */
    @GetMapping("/after/{year}/{mouth}/{day}/{num}")
    public Mono<ResultVO> getNewsByPublishedTimeAfter(@PathVariable int year,@PathVariable int mouth,@PathVariable int day,@PathVariable long num){
        if (num>100){
            return Mono.just(ResultVO.error(400,"一次性获取的数量不能大于100"));
        }
        Mono<ResultVO> resultVO;
        try {
            resultVO = newsDao
                    .getNewsByPublishedTimeAfter(LocalDateTime.of(year, mouth, day, 0, 0), num)
                    .collect(Collectors.toList())
                    .map(
                            e -> ResultVO.success(
                                    Map.of("news",e)
                            )
                    );

        }catch (DateTimeException e){
            resultVO= Mono.just(ResultVO.error(400,"时间错误"));
        }

        return resultVO;

    }

    @GetMapping("/{type}/{num}")
    public Mono<ResultVO> getNewsByType(@PathVariable int num, @PathVariable String type){
        if (num>100){
            return Mono.just(ResultVO.error(400,"一次性获取的数量不能大于100"));
        }
        if(!newsType.contains(type)) {
            return Mono.just(ResultVO.error(400,"新闻的类型必须为"+newsType));
        }
        return newsDao.getNewsByType(type,LocalDateTime.now(),num)
                .collectList()
                .map(e-> ResultVO.success(Map.of("news",e)));
    }

}
