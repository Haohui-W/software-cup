package com.haohui.softwarecup.softwarecupapi.handler;

import com.haohui.softwarecup.softwarecupapi.pojo.User;
import com.haohui.softwarecup.softwarecupapi.service.UserService;
import com.haohui.softwarecup.softwarecupapi.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserHandler {


    private final UserService userService;

    @Autowired
    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Mono<ResultVO> login(@RequestBody User user, ServerHttpResponse serverHttpResponse) {
        if (user.getUuid() == null) {
            UUID uuid = UUID.randomUUID();
            serverHttpResponse.getHeaders().add("uuid", uuid.toString());
            user.setUuid(uuid);
            Mono<Integer> userMono = userService.save(user);
            return userMono.map(i -> {
                if (i==1) {
                    return ResultVO.ok(Map.of("info", "登录成功"));
                }
                return ResultVO.innerErr("登录失败");
            });
        } else {
            Mono<User> userMono = userService.findById(user.getUuid());
            return userMono.map(e -> {
                if (e == null) {
                    return ResultVO.reqErr("用户不存在，请携带正确的uuid");
                } else {
                    return ResultVO.ok(Map.of("info", "登录成功"));
                }
            });
        }
    }
}
