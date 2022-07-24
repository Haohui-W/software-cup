package com.haohui.softwarecup.softwarecupapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haohui.softwarecup.softwarecupapi.dao.UserDao;
import com.haohui.softwarecup.softwarecupapi.pojo.User;
import com.haohui.softwarecup.softwarecupapi.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserDao userDao, ObjectMapper objectMapper) {
        this.userDao = userDao;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Integer> save(User user) {
        try {
            return userDao.save(
                    user.getUuid().toString(),
                    user.getNickName(),
                    user.getUsername(),
                    user.getUserPassword(),
                    objectMapper.writeValueAsString(user.getKeyWords()==null?new ArrayList<String>():user.getKeyWords())
                    );
        }
        catch (JsonProcessingException ignore){

        }
        return null;
    }

    @Override
    public Mono<User> findById(UUID uuid) {
        return userDao.findById(uuid);
    }

    @Override
    public Mono<Boolean> existUserByName(String username) {
        return userDao.exists(Example.of(User.builder().username(username).build()));
    }
}
