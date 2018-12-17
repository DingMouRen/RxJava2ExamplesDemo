package com.dingmouren.examplesforandroid.ui.operators;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.R;

/**
 * @author dingmouren
 */
public class OperatorsActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,OperatorsActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_operators;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.recycler_view);
    }
}
