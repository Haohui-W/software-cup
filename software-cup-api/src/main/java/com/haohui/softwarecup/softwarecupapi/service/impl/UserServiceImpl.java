package com.haohui.softwarecup.softwarecupapi.service.impl;

import com.alibaba.fastjson2.JSON;
import com.haohui.softwarecup.softwarecupapi.dao.UserDao;
import com.haohui.softwarecup.softwarecupapi.pojo.User;
import com.haohui.softwarecup.softwarecupapi.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Mono<Integer> save(User user) {
        return userDao.save(
                user.getUuid().toString(),
                user.getNickName(),
                user.getUsername(),
                user.getUserPassword(),
                JSON.toJSONString(user.getKeyWords()==null?new ArrayList<String>():user.getKeyWords()),
                user.getBirthDay()
        );
    }

    @Override
    public Mono<User> findById(UUID uuid) {
        return userDao.findById(uuid);
    }
}
