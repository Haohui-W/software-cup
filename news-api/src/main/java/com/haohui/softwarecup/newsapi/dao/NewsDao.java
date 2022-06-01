package com.haohui.softwarecup.newsapi.dao;

import com.haohui.softwarecup.newsapi.pojo.News;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsDao extends R2dbcRepository<News,Long> {
    @Query("SELECT * FROM software_cup.news WHERE nid=:nid")
    Mono<News> getNewsByNid(long nid);

    @Query("SELECT * FROM software_cup.news WHERE nid in (:ids)")
    Flux<News> getNRandomNews(List<Long> ids);

    @Query("SELECT * FROM software_cup.news WHERE published_time > :time LIMIT :num")
    Flux<News> getNewsByPublishedTimeAfter(LocalDateTime time,long num);
}
