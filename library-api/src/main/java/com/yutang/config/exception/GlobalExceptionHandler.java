package com.yutang.config.exception;

import com.yutang.config.ResultData;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author fzhuoxing
 * @Time 2022/4/14 2:53 下午
 * 全局异常处理
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 注解校验劫持
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultData handler(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        ObjectError objectError = result.getAllErrors().stream().findFirst().get();
        return ResultData.failMsg(objectError.getDefaultMessage());
    }

    // 运行时错误劫持
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public ResultData handler(RuntimeException e){
        return ResultData.failMsg(e.getMessage());
    }
}
