package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingmouren
 */
public class MergeOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean) {
        Intent intent = new Intent(context, MergeOperatorActivity.class);
        intent.putExtra(KEY, bean);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        if (getIntent() != null) {
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

        Observable.merge(getObservable_1(), getObservable_2())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String value) {
                        mainThreadTextLog("onNext = " + value);
                        Log.e(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 第一个Observable
     *
     * @return
     */
    private Observable<String> getObservable_1() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                Thread.sleep(500);
                mainThreadTextLog("Observable_1发射 a");
                emitter.onNext("a");


                Thread.sleep(500);
                mainThreadTextLog("Observable_1发射 b");
                emitter.onNext("b");


                Thread.sleep(500);
                mainThreadTextLog("Observable_1发射 c");
                emitter.onNext("c");

                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 第二个Observable
     *
     * @return
     */
    private Observable<String> getObservable_2() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Thread.sleep(300);
                mainThreadTextLog("Observable_2发射 A");
                emitter.onNext("A");


                Thread.sleep(300);
                mainThreadTextLog("Observable_2发射 B");
                emitter.onNext("B");


                Thread.sleep(300);
                mainThreadTextLog("Observable_2发射 C");
                emitter.onNext("C");


                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 主线程更新UI日志
     *
     * @param content
     */
    private void mainThreadTextLog(final String content) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvLog.append(content + "\n");
            }
        });
    }

}
