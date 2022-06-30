package com.yutang.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author fzhuoxing
 * @Time 2022/4/14 2:51 下午
 * 全局结果封装
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultData {

    private Integer code;
    private String msg;
    private Object data;

    public static ResultData succMsg(Object data){
        return new ResultData(200, "操作成功", data);
    }

    public static ResultData succ(Integer code, String msg, Object data){
        return new ResultData(200, msg, data);
    }

    public static ResultData fail(Integer code, String msg, Object data){
        return new ResultData(code, msg, data);
    }

    public static ResultData failMsg(String msg){
        return new ResultData(400, msg, null);
    }

}
