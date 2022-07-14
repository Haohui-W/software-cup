package com.haohui.softwarecup.softwarecupapi.service.impl;

import com.haohui.softwarecup.softwarecupapi.dao.NewsDao;
import com.haohui.softwarecup.softwarecupapi.pojo.News;
import com.haohui.softwarecup.softwarecupapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsDao newsDao;

    @Autowired
    public NewsServiceImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public Flux<News> recommend(UUID uuid) {
        return newsDao.findAll();
    }

    @Override
    public Flux<News> getNRandomNews(LocalDateTime time, long num) {
        return newsDao.getNRandomNews(time, num);
    }

    @Override
    public Flux<News> getNewsByPublishedTimeAfter(LocalDateTime time, long num) {
        return newsDao.getNewsByPublishedTimeAfter(time, num);
    }

    @Override
    public Flux<News> getNewsByType(LocalDateTime time, long num, String type) {
        return newsDao.getNewsByType(time, num, type);
    }
}
