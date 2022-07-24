package com.haohui.softwarecup.softwarecupapi.service;

import com.haohui.softwarecup.softwarecupapi.pojo.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Mono<Integer> save(User user);
    Mono<User> findById(UUID uuid);

    Mono<Boolean> existUserByName(String username);
}
