package com.dingmouren.examplesforandroid.model;

/**
 * @author dingmouren
 */
public class MyResponse<D> {
    public String msg;
    public int code;
    public D data;
    public MyResponse(String msg,int code,D data){
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
}
