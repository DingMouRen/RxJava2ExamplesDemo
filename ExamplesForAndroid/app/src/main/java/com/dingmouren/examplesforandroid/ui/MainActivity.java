package com.dingmouren.examplesforandroid.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.http.Api;
import com.dingmouren.examplesforandroid.http.HttpManager;
import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.model.Student;
import com.dingmouren.examplesforandroid.ui.examples.ExamplesActivity;
import com.dingmouren.examplesforandroid.ui.operators.OperatorsActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * 应用实例文章
 * https://www.jianshu.com/p/c935d0860186
 *
 * https://www.jianshu.com/p/b6a1939b3cdb
 */

public class MainActivity extends BaseActivity {

    public Button mBtnOperator;
    public Button mBtnExample;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBtnOperator = findViewById(R.id.btn_operator);
        mBtnExample = findViewById(R.id.btn_example);

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpManager.createService(Api.class).getStudentInfo()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MyResponse<Student>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.e("onSubscribe",d.toString());
                            }

                            @Override
                            public void onNext(MyResponse<Student> response) {

                                Log.e("onNext",response.data.toString());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("onError",e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.e("onComplete","onComplete");
                            }
                        });
            }
        });
    }

    @Override
    public void initListener() {
        mBtnOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperatorsActivity.newInstance(MainActivity.this);
            }
        });

        mBtnExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamplesActivity.newInstance(MainActivity.this);
            }
        });
    }
}
