package com.haohui.softwarecup.dbmanager.dao;

import com.haohui.softwarecup.dbmanager.pojo.News;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsDao extends R2dbcRepository<News, Long> {}
