package com.dingmouren.examplesforandroid.ui.examples.example_9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.http.Api;
import com.dingmouren.examplesforandroid.http.HttpManager;
import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.model.Nest1Bean;
import com.dingmouren.examplesforandroid.model.Nest2Bean;
import com.dingmouren.examplesforandroid.ui.examples.example_8.CountdownActivity;
import com.dingmouren.examplesforandroid.util.JsonUtils;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingmouren
 */
public class NestQuestActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    private Button mBtn;

    private TextView mTvLog;

    private EditText mEditText;


    public static void newInstance(Context context){
        context.startActivity(new Intent(context,NestQuestActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_nest_quest;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
        mBtn = findViewById(R.id.btn);
        mTvLog = findViewById(R.id.tv_log);
        mEditText = findViewById(R.id.edit);

        mTvTitle.setText(mActivity.getResources().getString(R.string.example_9_nest));
    }

    @Override
    public void initListener() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }

    private void requestData() {

        String inputStr = mEditText.getText().toString().trim();

        if (TextUtils.isEmpty(inputStr)){
            Toast.makeText(mActivity,"输入不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        int intputInt = Integer.parseInt(inputStr);

        HttpManager.createService(Api.class)
                .openFirstDoor(intputInt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<MyResponse<Nest1Bean>>() {
                    @Override
                    public void accept(MyResponse<Nest1Bean> nest1BeanMyResponse) throws Exception {

                        mainThreadTextLog("doOnNext:\n"+JsonUtils.formatJson(new Gson().toJson(nest1BeanMyResponse)));
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<MyResponse<Nest1Bean>, ObservableSource<MyResponse<Nest2Bean>>>() {
                    @Override
                    public ObservableSource<MyResponse<Nest2Bean>> apply(MyResponse<Nest1Bean> nest1BeanMyResponse) throws Exception {
                        mainThreadTextLog("获取第二道门得密码,去打开第二道门");
                        return HttpManager.createService(Api.class).openSecondDoor(nest1BeanMyResponse.data.password);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyResponse<Nest2Bean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mainThreadTextLog("onSubscribe");
                    }

                    @Override
                    public void onNext(MyResponse<Nest2Bean> nest2BeanMyResponse) {
                        mainThreadTextLog("onNext:\n"+JsonUtils.formatJson(new Gson().toJson(nest2BeanMyResponse)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainThreadTextLog("onError:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mainThreadTextLog("onComplete\n\n");
                    }
                });
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
