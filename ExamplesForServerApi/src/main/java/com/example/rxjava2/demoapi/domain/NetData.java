package com.example.rxjava2.demoapi.domain;

/**
 * 用于测试优先加载本机缓存，同时加载网络数据用例的Bean
 */
public class NetData {
    public String title;
    public String desc;
    public NetData(String title,String desc){
        this.title = title;
        this.desc = desc;
    }
}
