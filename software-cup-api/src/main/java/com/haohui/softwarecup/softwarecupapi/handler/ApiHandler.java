package com.haohui.softwarecup.softwarecupapi.handler;

import com.haohui.softwarecup.softwarecupapi.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author Haohui
 * 创建时间 2022/7/14/19:45
 */
@Controller
@CrossOrigin
@RequestMapping("/api")
public class ApiHandler {
    @GetMapping("/newsType")
    @ResponseBody
    public Mono<ResultVO> getNewsType(){
        return Mono.just(ResultVO.ok(Map.of("newsType", NewsHandler.newsType)));
    }
    @GetMapping
    public Mono<String> getAPI(){
        return Mono.just("redirect:https://flowus.cn/haohui/share/7447cad4-bcd8-4dc4-b992-3a6982406b66");
    }
}
