package com.dingmouren.examplesforandroid.ui.examples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.model.ExampleModel;
import com.dingmouren.examplesforandroid.ui.examples.example_1.DownloadActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_10.ZipActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_3.SearchActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_2.BufferActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_4.PollingActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_5.ExceptionRetryActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_6.ValidateActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_7.CacheActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_8.CountdownActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_9.NestQuestActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;

/**
 * @author dingmouren
 */
public class ExamplesActivity extends BaseActivity implements ExampleAdapter.OnExampleItemClickListener {

    private RecyclerView mRecyclerView;

    private ImageView mImgBack;

    private ExampleAdapter mAdapter;

    private List<ExampleModel> mExamplesList = new ArrayList<>();


    public static void newInstance(Context context){
        context.startActivity(new Intent(context,ExamplesActivity.class));
    }

    @Override
    public void init() {
        initExamples();
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_examples;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mRecyclerView = findViewById(R.id.recycler_view);
        mImgBack = findViewById(R.id.img_back);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ExampleAdapter(this,mExamplesList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAdapter.setOnExampleItemClickListener(this);
    }

    private void initExamples() {
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_1_download));
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_2_buffer));
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_3_search));
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_4_polling));
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_5_exception_retry));
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_6_validate));
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_7_cache));
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_8_count_down));
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_9_nest));
        mExamplesList.add(new ExampleModel(mActivity,R.string.example_10_zip));
    }

    @Override
    public void onExampleItemClick(int position, int strId) {
        switch (strId){
            case R.string.example_1_download:
                DownloadActivity.newInstance(mActivity);
                break;
            case R.string.example_2_buffer:
                BufferActivity.newInstance(mActivity);
                break;
            case R.string.example_3_search:
                SearchActivity.newInstance(mActivity);
                break;
            case R.string.example_4_polling:
                PollingActivity.newInstance(mActivity);
                break;
            case R.string.example_5_exception_retry:
                ExceptionRetryActivity.newInstance(mActivity);
                break;
            case R.string.example_6_validate:
                ValidateActivity.newInstance(mActivity);
                break;
            case R.string.example_7_cache:
                CacheActivity.newInstance(mActivity);
                break;
            case R.string.example_8_count_down:
                CountdownActivity.newInstance(mActivity);
                break;
            case R.string.example_9_nest:
                NestQuestActivity.newInstance(mActivity);
                break;
            case R.string.example_10_zip:
                ZipActivity.newInstance(mActivity);
                break;
        }
    }
}
