package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingmouren
 */
public class MapOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,MapOperatorActivity.class);
        intent.putExtra(KEY,bean);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        if (getIntent() != null){
            mBean = (OperatorModel) getIntent().getSerializableExtra(KEY);
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mTvOperatorName.setText(mBean.operatorName);
    }

    @Override
    protected void test() {
        mTvLog.append("\n\n");

        Observable.just(1,2,3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        mTvLog.append("map函数处理传递过来的数字\n");
                        //函数处理事件
                        String strResult = "数字变大2倍后："+integer * 2;

                        return strResult;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    Disposable mDisposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        mTvLog.append("onSubscribe 获取到Disposable实例 线程："+Thread.currentThread().getName()+"\n");
                    }

                    @Override
                    public void onNext(String s) {
                        mTvLog.append("onNext : "+s +"  线程:"+Thread.currentThread().getName()+"\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTvLog.append("onError   线程:"+Thread.currentThread().getName()+"\n");
                    }

                    @Override
                    public void onComplete() {
                        mTvLog.append("onComplete   线程:"+Thread.currentThread().getName()+"\n");
                        Log.e(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
                    }
                });
    }
}
