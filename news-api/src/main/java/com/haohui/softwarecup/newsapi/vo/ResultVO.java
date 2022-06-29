package com.haohui.softwarecup.newsapi.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    private int code;
    private String message;
    private Map<String, Object> data;

    public static ResultVO success(Map<String, Object> data) {
        ResultVO resultVO = new ResultVO();
        resultVO.code=200;
        resultVO.data=data;
        resultVO.message="";
        return resultVO;
    }

    public static ResultVO error(int code,String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.message=message;
        resultVO.code=code;
        resultVO.data=null;
        return resultVO;
    }


}
