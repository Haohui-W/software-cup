package com.haohui.softwarecup.softwarecupapi.dao;

import com.haohui.softwarecup.softwarecupapi.pojo.UserBehavior;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserBehaviorDao extends R2dbcRepository<Long, UserBehavior> {
}
