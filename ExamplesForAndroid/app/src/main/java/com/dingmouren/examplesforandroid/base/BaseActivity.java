package com.dingmouren.examplesforandroid.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dingmouren.examplesforandroid.model.OperatorModel;

/**
 * @author dingmouren
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mActivity = this;

        init();

        setContentView(getLayoutId());

        initView(savedInstanceState);

        initListener();

        initData();

    }

    /**
     * 初始化
     */
    public void init(){}

    /**
     * 布局id
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化布局
     * @param savedInstanceState
     * @return
     */
    public abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化监听
     */
    public void initListener(){}

    /**
     * 初始化数据
     */
    public void initData(){}

    /**
     * 吐司
     */
    public void show(String content){
        Toast.makeText(mActivity,content,Toast.LENGTH_SHORT).show();
    }
}
