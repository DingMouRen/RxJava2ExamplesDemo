package com.dingmouren.examplesforandroid.ui.examples.example_7;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_6.ValidateActivity;

/**
 * @author dingmouren
 */
public class CacheActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,CacheActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_7_cache;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);

        mTvTitle.setText(mActivity.getResources().getString(R.string.example_7_cache));
    }

    @Override
    public void initListener() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
