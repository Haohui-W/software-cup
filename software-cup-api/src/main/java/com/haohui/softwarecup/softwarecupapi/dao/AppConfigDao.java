package com.haohui.softwarecup.softwarecupapi.dao;

import com.haohui.softwarecup.softwarecupapi.pojo.AppConfig;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface AppConfigDao extends R2dbcRepository<AppConfig,Long> {

}
