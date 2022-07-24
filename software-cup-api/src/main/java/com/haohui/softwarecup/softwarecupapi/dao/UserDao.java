package com.haohui.softwarecup.softwarecupapi.dao;

import com.haohui.softwarecup.softwarecupapi.pojo.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface UserDao extends R2dbcRepository<User,UUID > {
    @Query("INSERT INTO software_cup_db.user(uuid, nick_name, username, user_password, key_words, birthday) values (:uuid,:nickName,:username,:userPassword)")
    @Modifying
    Mono<Integer> save(String uuid, String nickName, String username, String userPassword, String keyWords);
}
