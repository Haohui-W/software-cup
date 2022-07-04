package com.haohui.softwarecup.softwarecupapi.dao;

import com.haohui.softwarecup.softwarecupapi.pojo.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import java.util.UUID;

public interface UserDao extends R2dbcRepository<UUID, User> {
}
