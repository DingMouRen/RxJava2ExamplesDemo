package com.dingmouren.examplesforandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author dingmouren
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getLayoutId() != 0){
            setContentView(getLayoutId());
        }else {
            super.onCreate(savedInstanceState);
        }

        initView(savedInstanceState);

    }

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
}
