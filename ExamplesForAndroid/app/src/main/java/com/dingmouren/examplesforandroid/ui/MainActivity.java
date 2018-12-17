package com.dingmouren.examplesforandroid.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.ui.examples.ExamplesActivity;
import com.dingmouren.examplesforandroid.ui.operators.OperatorsActivity;

public class MainActivity extends BaseActivity {

    public Button mBtnOperator;
    public Button mBtnExample;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBtnOperator = findViewById(R.id.btn_operator);
        mBtnExample = findViewById(R.id.btn_example);
    }

    @Override
    public void initListener() {
        mBtnOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperatorsActivity.newInstance(MainActivity.this);
            }
        });

        mBtnExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamplesActivity.newInstance(MainActivity.this);
            }
        });
    }
}
