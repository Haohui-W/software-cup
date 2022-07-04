package com.haohui.softwarecup.softwarecupapi.dao;

import com.haohui.softwarecup.softwarecupapi.pojo.UserLog;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserLogDao extends R2dbcRepository<Long, UserLog> {

}
