package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CreateOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context,OperatorModel bean){
        Intent intent = new Intent(context,CreateOperatorActivity.class);
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
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                mTvLog.append("Observable 发射 1\n");
                emitter.onNext(1);

                mTvLog.append("Observable 发射 2\n");
                emitter.onNext(2);

                mTvLog.append("Observable 发射 3\n");
                emitter.onNext(3);

                mTvLog.append("Observable 发射 4\n");
                emitter.onNext(4);

                emitter.onComplete();

            }

        }).subscribe(new Observer<Integer>() {

            Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable disposable) {
                mDisposable = disposable;
                mTvLog.append("onSubscribe 获取到Disposable实例\n");
            }

            @Override
            public void onNext(Integer integer) {

                mTvLog.append("onNext -- "+integer+"  Disposable的订阅状态:"+mDisposable.isDisposed()+"->"+(mDisposable.isDisposed()?"解除订阅":"订阅中")+"\n");

                if (integer == 2){
                    mDisposable.dispose();
                    mTvLog.append("onNext -- "+integer+"  Disposable的订阅状态:"+mDisposable.isDisposed()+"->"+(mDisposable.isDisposed()?"解除订阅":"订阅中")+"\n");
                }
            }

            @Override
            public void onError(Throwable e) {
                mTvLog.append("onError:"+e.getMessage());
            }

            @Override
            public void onComplete() {
                mTvLog.append("onComplete");
            }
        });

        Log.e(mActivity.getClass().getSimpleName(), mTvLog.getText().toString());
    }
}
