package com.haohui.softwarecup.softwarecupapi.service;

import com.haohui.softwarecup.softwarecupapi.pojo.News;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

public interface NewsService {
    Flux<News> recommend(UUID uuid);

    Flux<News> getNRandomNews(LocalDateTime time,long num);

    Flux<News> getNewsByPublishedTimeAfter(LocalDateTime time,long num);

    Flux<News> getNewsByType(LocalDateTime time,long num,String type);
}
