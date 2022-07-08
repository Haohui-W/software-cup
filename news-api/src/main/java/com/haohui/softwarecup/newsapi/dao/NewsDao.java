package com.haohui.softwarecup.newsapi.dao;

import com.haohui.softwarecup.newsapi.pojo.News;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface NewsDao extends R2dbcRepository<News,Long> {
    @Query("SELECT * FROM software_mock.news WHERE nid=:nid")
    Mono<News> getNewsByNid(long nid);

    @Query("SELECT * FROM software_mock.news WHERE published_time<=:time ORDER BY rand() LIMIT :num")
    Flux<News> getNRandomNews(LocalDateTime time,long num);

    @Query("SELECT * FROM software_mock.news WHERE published_time > :time LIMIT :num")
    Flux<News> getNewsByPublishedTimeAfter(LocalDateTime time,long num);

    @Query("SELECT * FROM software_mock.news WHERE type=:type AND published_time<= :time ORDER BY rand() LIMIT :num")
    Flux<News> getNewsByType(String type,LocalDateTime time,int num);
}
