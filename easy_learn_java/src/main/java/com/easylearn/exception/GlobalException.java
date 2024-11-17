package com.easylearn.exception;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.easylearn.pojo.vo.Result;
@RestControllerAdvice//用来做全局异常处理器的
public class GlobalException {


    @ExceptionHandler(BadCredentialsException.class)
    public Result ex(BadCredentialsException e)
    {
        e.printStackTrace();
        return Result.error("账号或者密码错误");
    }

    @ExceptionHandler({Exception.class,Throwable.class})
    public Result ex(Exception e)
    {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

}
