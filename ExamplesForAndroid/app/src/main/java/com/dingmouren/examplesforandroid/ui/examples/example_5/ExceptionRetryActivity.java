package com.dingmouren.examplesforandroid.ui.examples.example_5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_4.PollingActivity;

/**
 * @author dingmouren
 */
public class ExceptionRetryActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,ExceptionRetryActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_5_exception_retry;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
    }
}
