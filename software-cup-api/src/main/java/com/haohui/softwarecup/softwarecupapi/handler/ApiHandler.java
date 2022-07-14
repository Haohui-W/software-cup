package com.haohui.softwarecup.softwarecupapi.handler;

import com.haohui.softwarecup.softwarecupapi.vo.ResultVO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author Haohui
 * 创建时间 2022/7/14/19:45
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiHandler {
    @GetMapping("/newsType")
    public Mono<ResultVO> getNewsType(){
        return Mono.just(ResultVO.ok(Map.of("newsType", NewsHandler.newsType)));
    }
    @GetMapping
    public Mono<String> getAPI(){
        String res = "<a href=\"https://flowus.cn/haohui/share/7447cad4-bcd8-4dc4-b992-3a6982406b66\">api文档</a>";
        return Mono.just(res);
    }
}
