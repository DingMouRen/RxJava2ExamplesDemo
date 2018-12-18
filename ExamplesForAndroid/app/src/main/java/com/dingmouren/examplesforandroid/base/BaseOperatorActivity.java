package com.dingmouren.examplesforandroid.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;

/**
 * @author dingmouren
 */
public abstract class BaseOperatorActivity extends BaseActivity {


    protected TextView mTvOperatorName;
    protected ImageView mImgBack;
    protected TextView mTvLog;
    protected Button mBtnTest;
    protected ScrollView mScrollView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_basee_operator;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvOperatorName = findViewById(R.id.tv_operator_name);
        mImgBack = findViewById(R.id.img_back);
        mTvLog = findViewById(R.id.tv_log);
        mBtnTest = findViewById(R.id.btn_test);
        mScrollView = findViewById(R.id.scroll_view);
    }

    @Override
    public void initListener() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    protected abstract void test();
}
