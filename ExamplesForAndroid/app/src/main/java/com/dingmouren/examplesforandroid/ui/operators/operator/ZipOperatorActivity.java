package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

        Observable.zip(getNumberObservable(), getLetterObservable(), new BiFunction<Integer ,String,String>() {
            @Override
            public String apply(Integer number, String letter) throws Exception {
                mTvLog.append("zip  number:"+number+"  letter:"+letter +"\n");
                return number + letter;
            }


        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("onSubscribe\n");
            }

            @Override
            public void onNext(String result) {
                mTvLog.append("onNext result:"+result+"\n");
            }

            @Override
            public void onError(Throwable e) {
                mTvLog.append("onError "+e.getMessage()+"\n");
            }

            @Override
            public void onComplete() {
                mTvLog.append("onComplete \n");
                Log.e(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
            }
        });
    }

    //获取数字的Observable实例
    private Observable getNumberObservable(){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                mTvLog.append("NumberObservalbe 发射 1\n");
                emitter.onNext(1);

                mTvLog.append("NumberObservalbe 发射 2\n");
                emitter.onNext(2);

                mTvLog.append("NumberObservalbe 发射 3\n");
                emitter.onNext(3);

                emitter.onComplete();
            }
        });
    }

    //获取字母的Observable实例
    private Observable getLetterObservable(){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                mTvLog.append("NumberObservalbe 发射 A\n");
                emitter.onNext("A");

                mTvLog.append("NumberObservalbe 发射 B\n");
                emitter.onNext("B");

                mTvLog.append("NumberObservalbe 发射 C\n");
                emitter.onNext("C");

                mTvLog.append("NumberObservalbe 发射 D\n");
                emitter.onNext("D");

                emitter.onComplete();
            }
        });
    }
}
