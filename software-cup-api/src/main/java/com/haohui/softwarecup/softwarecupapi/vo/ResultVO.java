package com.haohui.softwarecup.softwarecupapi.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResultVO {
    private int code;
    private String msg;
    private Map<String,Object> data;


    public static ResultVO ok(Map<String,Object> data){
        return ResultVO.builder().code(200).data(data).build();
    }
    public static ResultVO reqErr(String msg){
        return ResultVO.builder().code(400).msg(msg).build();
    }

    public static ResultVO innerErr(String msg){
        return ResultVO.builder().code(500).msg(msg).build();
    }
}
