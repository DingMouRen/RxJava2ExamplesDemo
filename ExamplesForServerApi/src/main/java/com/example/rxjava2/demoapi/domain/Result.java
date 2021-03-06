package com.example.rxjava2.demoapi.domain;

/**
 * 统一返回的数据格式
 * @param <T>
 */
public class Result<T> {
    /*状态码*/
    private Integer code;
    /*提示信息*/
    private String msg;
    /*具体内容*/
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
