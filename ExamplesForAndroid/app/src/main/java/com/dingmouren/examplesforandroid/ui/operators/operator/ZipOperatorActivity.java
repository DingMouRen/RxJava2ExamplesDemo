package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * @author dingmouren
 */
public class ZipOperatorActivity extends BaseOperatorActivity {
    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,ZipOperatorActivity.class);
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

        Observable.zip(getNumberObservable(), getLetterObservable(), new BiFunction<String ,Integer,String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }


        }).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //获取数字的Observable实例
    private Observable getNumberObservable(){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                mTvLog.append("NumberObservalbe 发射 1");
                emitter.onNext(1);

                mTvLog.append("NumberObservalbe 发射 2");
                emitter.onNext(2);

                mTvLog.append("NumberObservalbe 发射 3");
                emitter.onNext(3);
            }
        });
    }

    //获取字母的Observable实例
    private Observable getLetterObservable(){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                mTvLog.append("NumberObservalbe 发射 A");
                emitter.onNext("A");

                mTvLog.append("NumberObservalbe 发射 B");
                emitter.onNext("B");

                mTvLog.append("NumberObservalbe 发射 C");
                emitter.onNext("C");

                mTvLog.append("NumberObservalbe 发射 D");
                emitter.onNext("D");
            }
        });
    }
}
