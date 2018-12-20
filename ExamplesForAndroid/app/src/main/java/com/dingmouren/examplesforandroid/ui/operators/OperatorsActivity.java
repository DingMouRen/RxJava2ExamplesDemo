package com.dingmouren.examplesforandroid.ui.operators;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;

import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.model.OperatorModel;
import com.dingmouren.examplesforandroid.ui.operators.operator.BufferOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.CompletableOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.ConcatMapOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.ConcatOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.CreateOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.DebounceOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.DeferOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.DistinctOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.DoOnNextOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.FilterOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.FlatMapOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.IntervalOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.JustOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.LastOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.MapOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.MayBeOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.SingleActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.SkipOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.TakeOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.TimerOperatorActivity;
import com.dingmouren.examplesforandroid.ui.operators.operator.ZipOperatorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingmouren
 */
public class OperatorsActivity extends BaseActivity implements OperatorsAdapter.OnItemClickListener {


    private RecyclerView mRecyclerView;

    private ImageView mImgBack;

    private OperatorsAdapter mAdapter;

    private List<OperatorModel> mOperatorsList = new ArrayList<>();


    public static void newInstance(Context context){
        context.startActivity(new Intent(context,OperatorsActivity.class));
    }

    @Override
    public void init() {
        initOperatorsList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_operators;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new OperatorsAdapter(this,mOperatorsList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
    }

    private void initOperatorsList(){
        mOperatorsList.add(new OperatorModel(this,R.string.rx_create,R.string.rx_create_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_just,R.string.rx_just_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_map,R.string.rx_map_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_zip,R.string.rx_zip_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_flatMap,R.string.rx_flatMap_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_concatMap,R.string.rx_concatMap_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_filter,R.string.rx_filter_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_take,R.string.rx_take_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_doOnNext,R.string.rx_doOnNext_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_timer,R.string.rx_timer_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_interval,R.string.rx_interval_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_skip,R.string.rx_skip_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_concat,R.string.rx_concat_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_distinct,R.string.rx_distinct_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_buffer,R.string.rx_buffer_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_debounce,R.string.rx_debounce_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_defer,R.string.rx_defer_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_last,R.string.rx_last_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_merge,R.string.rx_merge_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_reduce,R.string.rx_reduce_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_scan,R.string.rx_scan_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_window,R.string.rx_window_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_PublishSubject,R.string.rx_PublishSubject_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_AsyncSubject,R.string.rx_AsyncSubject_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_BehaviorSubject,R.string.rx_BehaviorSubject_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_single,R.string.rx_single_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_Completable,R.string.rx_Completable_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_MayBe,R.string.rx_MayBe_desc));
        mOperatorsList.add(new OperatorModel(this,R.string.rx_Flowable,R.string.rx_Flowable_desc));
    }

    @Override
    public void onItemClick(OperatorModel model, int position) {
        switch (model.operatorId){
            case R.string.rx_create:
                CreateOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_just:
                JustOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_map:
                MapOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_zip:
                ZipOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_flatMap:
                FlatMapOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_concatMap:
                ConcatMapOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_filter:
                FilterOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_take:
                TakeOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_doOnNext:
                DoOnNextOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_timer:
                TimerOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_interval:
                IntervalOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_skip:
                SkipOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_concat:
                ConcatOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_distinct:
                DistinctOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_buffer:
                BufferOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_debounce:
                DebounceOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_defer:
                DeferOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_last:
                LastOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_merge:
                break;
            case R.string.rx_reduce:
                break;
            case R.string.rx_scan:
                break;
            case R.string.rx_window:
                break;
            case R.string.rx_PublishSubject:
                break;
            case R.string.rx_AsyncSubject:
                break;
            case R.string.rx_BehaviorSubject:
                break;
            case R.string.rx_single:
                SingleActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_Completable:
                CompletableOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_MayBe:
                MayBeOperatorActivity.newInstance(mActivity,model);
                break;
            case R.string.rx_Flowable:
                break;
        }
    }
}
