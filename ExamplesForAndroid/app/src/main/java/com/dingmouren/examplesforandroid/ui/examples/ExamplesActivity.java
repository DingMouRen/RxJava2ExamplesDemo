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
import com.dingmouren.examplesforandroid.ui.examples.example_3.SearchActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_2.BufferActivity;

import java.util.ArrayList;
import java.util.List;

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
        }
    }
}
