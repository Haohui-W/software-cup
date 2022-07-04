package com.haohui.softwarecup.softwarecupapi.dao;

import com.haohui.softwarecup.softwarecupapi.pojo.News;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface NewsDao extends R2dbcRepository<Long, News> {

}
