package com.haohui.softwarecup.softwarecupapi.dao;

import com.haohui.softwarecup.softwarecupapi.pojo.News;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface NewsDao extends R2dbcRepository<News, Long> {

    @Query("SELECT * FROM news WHERE type=:type AND published_time<= :time ORDER BY rand() LIMIT :num")
    Flux<News> getNewsByType(LocalDateTime time, long num, String type);

    @Query("SELECT * FROM news WHERE published_time<=:time ORDER BY rand() LIMIT :num")
    Flux<News> getNRandomNews(LocalDateTime time, long num);

    @Query("SELECT * FROM ews WHERE published_time > :time ORDER BY rand() LIMIT :num")
    Flux<News> getNewsByPublishedTimeAfter(LocalDateTime time, long num);
}
