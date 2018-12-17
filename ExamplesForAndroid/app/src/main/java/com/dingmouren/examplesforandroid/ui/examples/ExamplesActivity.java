package com.dingmouren.examplesforandroid.ui.examples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.ui.operators.OperatorsActivity;

/**
 * @author dingmouren
 */
public class ExamplesActivity extends BaseActivity {

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,ExamplesActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_examples;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}
