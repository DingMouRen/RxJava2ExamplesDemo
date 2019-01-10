package com.dingmouren.examplesforandroid.ui.examples.example_7;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.http.Api;
import com.dingmouren.examplesforandroid.http.HttpManager;
import com.dingmouren.examplesforandroid.model.CacheToNetData;
import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.ui.examples.example_6.ValidateActivity;
import com.dingmouren.examplesforandroid.util.JsonUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingmouren
 */
public class CacheActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    private Button mBtn;

    private Button mBtnClear;

    private TextView mTvLog;

    private RecyclerView mRecyclerView;

    private ProgressBar mProgressBar;

    private EditText mEditLocal;

    private EditText mEditNet;

    private CompositeDisposable mCompositeDisposable;

    private MyAdapter mAdapter;


    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, CacheActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_7_cache;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
        mBtn = findViewById(R.id.btn);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mTvLog = findViewById(R.id.tv_log);
        mEditLocal = findViewById(R.id.edit_local);
        mEditNet = findViewById(R.id.edit_net);
        mBtnClear = findViewById(R.id.btn_clear);

        mTvTitle.setText(mActivity.getResources().getString(R.string.example_7_cache));

        mCompositeDisposable = new CompositeDisposable();

        mAdapter = new MyAdapter();
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
        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCompositeDisposable != null) mCompositeDisposable.clear();
                mAdapter.clear();
                mTvLog.setText("");
                mProgressBar.setVisibility(View.GONE);
            }
        });
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timeStrLocal = mEditLocal.getText().toString().trim();
                String timeStrNet = mEditNet.getText().toString().trim();

                long timeLongLocal = 0;
                long timeLongNet = 0;

                if (!TextUtils.isEmpty(timeStrLocal)) {
                    timeLongLocal = Long.parseLong(timeStrLocal);
                }

                if (!TextUtils.isEmpty(timeStrNet)) {
                    timeLongNet = Long.parseLong(timeStrNet);
                }

                requestData(timeLongLocal, timeLongNet);
            }
        });
    }

    /**
     * 优先加载本地缓存数据，同时请求网络数据
     */
    private void requestData(final long delayTimeLocal, long delayTimeNet) {

        mProgressBar.setVisibility(View.VISIBLE);

        Observable<MyResponse<List<CacheToNetData>>> observable =
                getNetData(delayTimeNet).publish(new Function<Observable<MyResponse<List<CacheToNetData>>>, ObservableSource<MyResponse<List<CacheToNetData>>>>() {
                    @Override
                    public ObservableSource<MyResponse<List<CacheToNetData>>> apply(Observable<MyResponse<List<CacheToNetData>>> netResponseObservable) throws Exception {

                        return Observable.merge(getLocalCacheData(delayTimeLocal),netResponseObservable )
                                .takeUntil(new Predicate<MyResponse<List<CacheToNetData>>>() {
                                    @Override
                                    public boolean test(MyResponse<List<CacheToNetData>> listMyResponse) throws Exception {
                                        mainThreadTextLog("获取到的数据类型:"+listMyResponse.msg);
                                        return listMyResponse.msg.equals("成功");
                                    }
                                });
                    }
                });


        DisposableObserver<MyResponse<List<CacheToNetData>>> disposableObserver =
                new DisposableObserver<MyResponse<List<CacheToNetData>>>() {
                    @Override
                    public void onNext(MyResponse<List<CacheToNetData>> listMyResponse) {

                        mProgressBar.setVisibility(View.GONE);

                        if (listMyResponse.code == 1) {
                            if (listMyResponse.msg.equals("本地数据")) {
                                mainThreadTextLog("onNext --- 加载了本地数据");
                            } else {
                                mainThreadTextLog("onNext --- 加载了网络数据");
                            }
                            mAdapter.setData(listMyResponse.data);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        mainThreadTextLog("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mainThreadTextLog("onComplete");
                    }
                };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
    }

    /**
     * 获取本地缓存数据
     */
    public Observable<MyResponse<List<CacheToNetData>>> getLocalCacheData(final long delayTime) {
        return Observable.create(new ObservableOnSubscribe<MyResponse<List<CacheToNetData>>>() {
            @Override
            public void subscribe(ObservableEmitter<MyResponse<List<CacheToNetData>>> emitter) throws Exception {
                try {
                    mainThreadTextLog("开始加载本地缓存数据");

                    Thread.sleep(delayTime);

                    List<CacheToNetData> list = new ArrayList<>();

                    for (int i = 0; i < 10; i++) {
                        CacheToNetData bean = new CacheToNetData("来自本地缓存", "数据项 --- " + i);
                        list.add(bean);
                    }
                    mainThreadTextLog("结束加载本地缓存数据");
                    emitter.onNext(new MyResponse<List<CacheToNetData>>("本地数据", 1, list));
                    emitter.onComplete();


                } catch (Exception e) {
                    mainThreadTextLog("加载本地缓存数据异常：" + e.getMessage());
                    if (!emitter.isDisposed()) emitter.onError(e);
                }

            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 获取网络数据
     *
     * @param delayTime
     * @return
     */
    public Observable<MyResponse<List<CacheToNetData>>> getNetData(long delayTime) {
        mainThreadTextLog("开始请求网络数据");
        return HttpManager.createService(Api.class)
                .getNetData(delayTime)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends MyResponse<List<CacheToNetData>>>>() {
                    @Override
                    public ObservableSource<? extends MyResponse<List<CacheToNetData>>> apply(Throwable throwable) throws Exception {
                        mainThreadTextLog("请求网络数据失败：" + throwable.getMessage());
                        return Observable.never();
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

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<CacheToNetData> mList;

        public void setData(List<CacheToNetData> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_cache_to_net, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            CacheToNetData bean = mList.get(position);
            if (bean == null) return;
            holder.mTvTitle.setText(bean.title);
            holder.mTvDesc.setText(bean.desc);
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        public void clear() {
            mList.clear();
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView mTvTitle, mTvDesc;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mTvTitle = itemView.findViewById(R.id.tv_title);
                mTvDesc = itemView.findViewById(R.id.tv_desc);
            }
        }

    }

}
