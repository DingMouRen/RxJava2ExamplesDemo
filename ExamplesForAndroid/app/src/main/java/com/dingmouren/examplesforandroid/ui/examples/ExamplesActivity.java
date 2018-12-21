package com.dingmouren.examplesforandroid.ui.examples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.model.ExampleModel;
import com.dingmouren.examplesforandroid.model.OperatorModel;
import com.dingmouren.examplesforandroid.ui.operators.OperatorsActivity;
import com.dingmouren.examplesforandroid.ui.operators.OperatorsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingmouren
 */
public class ExamplesActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private ImageView mImgBack;

    private OperatorsAdapter mAdapter;

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

    }

    private void initExamples() {

    }
}
