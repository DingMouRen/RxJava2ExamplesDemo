package com.example.rxjava2.demoapi.util;


import com.example.rxjava2.demoapi.domain.Result;

/**
 * 统一返回数据格式的工具类
 */
public class ResultUtil {

    /**
     * 成功返回
     * @param object
     * @return
     */
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(1);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    /**
     * 成功返回，但是没有具体内容
     */
    public static Result success(){
        return success(null);
    }

    /**
     * 错误时返回
     */
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
