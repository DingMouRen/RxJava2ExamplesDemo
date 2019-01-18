package com.dingmouren.examplesforandroid.ui.examples.example_3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.http.Api;
import com.dingmouren.examplesforandroid.http.HttpManager;
import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.util.JsonUtils;
import com.dingmouren.examplesforandroid.util.LogUtils;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * 应用场景：
 * 客户端通过EditText的addTextChangedListener方法监听输入框的变化，
 * 当输入框发生变化之后就会回调afterTextChanged方法，客户端利用当前输入框内的文字向服务器发起请求，
 * 服务器返回与该搜索文字关联的结果给客户端进行展示。
 *
 * 问题：
 * 1.在用户连续输入的情况下，可能会发起某些不必要的请求。例如用户输入了abc，那么按照上面的实现，客户端就会发起a、ab、abc三个请求。
 * 2.当搜索词为空时，不应该发起请求。
 * 3.如果用户依次输入了ab和abc，那么首先会发起关键词为ab请求，之后再发起abc的请求，但是abc的请求如果先于ab的请求返回，
 * 那么就会造成用户期望搜索的结果为abc，最终展现的结果却是和ab关联的。
 *
 * 解决方案：
 * 1.使用debounce操作符，当输入框发生变化时，不会立刻将事件发送给下游，而是等待200ms，如果在这段事件内，输入框没有发生变化，那么才发送该事件；
 * 反之，则在收到新的关键词后，继续等待200ms。
 * 2.使用filter操作符，只有关键词的长度大于0时才发送事件给下游。
 * 3.使用switchMap操作符，这样当发起了abc的请求之后，即使ab的结果返回了，也不会发送给下游，从而避免了出现前面介绍的搜索词和联想结果不匹配的问题
 * switchMap操作符会保存最新的Observable产生的结果而舍弃旧的结果，当上一个任务尚未完成时，就开始下一个任务的话，上一个任务就会被取消掉。（注意需要在不同的线程）
 * @author dingmouren
 */
public class SearchActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    private EditText mEditText;

    private TextView mTvLog;

    private PublishSubject<String> mPublishSubject;

    private DisposableObserver<MyResponse<String>> mDisposableObserver;

    private CompositeDisposable mCompositeDisposable;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,SearchActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_3_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
        mEditText = findViewById(R.id.edit_text);
        mTvLog = findViewById(R.id.tv_log);

        mTvTitle.setText(getResources().getString(R.string.example_3_search));

        initObservable();
    }


    @Override
    public void initListener() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                toSearch(s.toString());
            }
        });
    }

    private void toSearch(String query) {
        mPublishSubject.onNext(query);
    }

    /**
     * 初始化Observable
     */
    private void initObservable() {

        mPublishSubject = PublishSubject.create();

        mDisposableObserver = new DisposableObserver<MyResponse<String>>() {//Disposable是一个抽象的观察者，可以通过disposable进行异步取消
            @Override
            public void onNext(MyResponse<String> myResponse) {
                Gson gson = new Gson();
                mTvLog.setText(JsonUtils.formatJson(gson.toJson(myResponse)));

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mPublishSubject.debounce(200, TimeUnit.MILLISECONDS)//不会发射时间间隔小于200毫秒的，
                .filter(new Predicate<String>() {//过滤操作符，只有字符串长度大于0才能发射
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.length() > 0;
                    }
                 //switchMap操作符会保存最新的Observable产生的结果而舍弃旧的结果，当上一个任务尚未完成时，就开始下一个任务的话，上一个任务就会被取消掉
                }).switchMap(new Function<String, ObservableSource<MyResponse<String>>>() {
            @Override
            public ObservableSource<MyResponse<String >> apply(String s) throws Exception {
                return HttpManager.createService(Api.class).search(s).subscribeOn(Schedulers.io());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDisposableObserver);

        mCompositeDisposable = new CompositeDisposable();//用于取消订阅关系

        mCompositeDisposable.add(mDisposableObserver);//添加到订阅关系
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
    }
}
