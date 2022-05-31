package com.haohui.softwarecup.dbmanager.dao;

import com.haohui.softwarecup.dbmanager.pojo.News;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface NewsDao extends R2dbcRepository<News, Long> {

    @Query("SELECT * FROM software_cup.news;")
    Flux<News> getAll();

    @Query("SELECT * FROM software_cup.news WHERE nid=:id")
    Mono<News> getANewsById(long id);

    @Modifying
    @Query("DELETE FROM software_cup.news WHERE nid=:id")
    Mono<Integer> deleteANEws(long id);

    @Modifying
    @Query("INSERT INTO software_cup.news VALUES (:nid,:title,:content,:type,:localDateTime)")
    void insertANews(long nid, String title, String content, String type,LocalDateTime localDateTime);
}
