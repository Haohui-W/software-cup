package com.haohui.softwarecup.softwarecupapi.service;

import com.haohui.softwarecup.softwarecupapi.pojo.News;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface NewsService {
    Flux<News> recommend(UUID uuid);
}
