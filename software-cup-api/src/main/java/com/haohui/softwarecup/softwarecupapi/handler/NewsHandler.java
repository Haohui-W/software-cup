package com.haohui.softwarecup.softwarecupapi.handler;

import com.haohui.softwarecup.softwarecupapi.pojo.News;
import com.haohui.softwarecup.softwarecupapi.service.NewsService;
import com.haohui.softwarecup.softwarecupapi.service.UserService;
import com.haohui.softwarecup.softwarecupapi.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@CrossOrigin
public class NewsHandler {

    public static final List<String> newsType = List.of("体育", "娱乐", "家居", "彩票", "房产", "教育", "时尚", "时政", "星座", "游戏", "社会", "科技", "股票", "财经");

    private final NewsService newsService;
    private final UserService userService;


    private final Queue<News> newsQueue = new LinkedList<>();

    @Autowired
    public NewsHandler(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    /*
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
    */

    @GetMapping("/push")
    public Mono<ResultVO> pushNews() {
        News news = newsQueue.poll();
        if (news == null) return Mono.just(ResultVO.reqErr("没有新的推送"));
        return Mono.just(ResultVO.ok(Map.of("news", news)));
    }

    @PostMapping("/push")
    public Mono<ResultVO> postPushNews(@RequestBody News news) {
        news.setPublishedTime(LocalDateTime.now(ZoneId.of("UTC+8")));
        return Mono.just(ResultVO.ok(Map.of("success", newsQueue.add(news))));
    }

    /**
     * 随机获取num个新闻
     * num必须不大于100
     *
     * @param num 数量
     * @return resultVO
     */
    @GetMapping("/{num}")
    public Mono<ResultVO> getNRandomNews(@PathVariable long num) {
        if (num > 100) {
            return Mono.just(ResultVO.reqErr("一次性获取的数量不能大于100"));
        }
        return newsService.getNRandomNews(LocalDateTime.now(ZoneId.of("UTC+8")), num).collectList().map(e -> ResultVO.ok(Map.of("news", e)));
    }

    /**
     * 根据日期获取num个大于日期的新闻
     * 日期无效时返回错误
     * 数量大于100也错误
     *
     * @param year  年
     * @param mouth 月
     * @param day   日
     * @param num   数量
     * @return resultVO
     */
    @GetMapping("/after/{year}/{mouth}/{day}/{num}")
    public Mono<ResultVO> getNewsByPublishedTimeAfter(@PathVariable int year, @PathVariable int mouth, @PathVariable int day, @PathVariable long num) {
        if (num > 100) {
            return Mono.just(ResultVO.reqErr("一次性获取的数量不能大于100"));
        }
        try {
            return newsService.getNewsByPublishedTimeAfter(LocalDateTime.of(year, mouth, day, 0, 0), num).collect(Collectors.toList()).map(e -> ResultVO.ok(Map.of("news", e)));

        } catch (DateTimeException e) {
            return Mono.just(ResultVO.reqErr("时间错误"));
        }
    }

    /**
     * 根据类型，随机获取num条新闻
     *
     * @param num  新闻数量
     * @param type 新闻类型
     * @return 新闻结果
     */
    @GetMapping("/{type}/{num}")
    public Mono<ResultVO> getNewsByType(@PathVariable int num, @PathVariable String type) {
        if (num > 100) {
            return Mono.just(ResultVO.reqErr("一次性获取的数量不能大于100"));
        }
        if (!newsType.contains(type)) {
            return Mono.just(ResultVO.reqErr("新闻的类型必须为" + newsType));
        }
        return newsService.getNewsByType(LocalDateTime.now(ZoneId.of("UTC+8")), num, type).collectList().map(e -> ResultVO.ok(Map.of("news", e)));
    }
}
