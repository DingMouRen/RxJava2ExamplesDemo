package com.dingmouren.examplesforandroid.ui.examples.example_6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.ui.examples.example_5.ExceptionRetryActivity;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * @author dingmouren
 */
public class ValidateActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    private EditText mEditAccount,mEditPwd;

    private TextView mTvLogin;

    private CompositeDisposable mCompositeDisposable;//管理订阅关系

    private PublishSubject<String> mAccoountPublishSubject;

    private PublishSubject<String> mPwdPublishSubject;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,ValidateActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_6_validate;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
        mEditAccount = findViewById(R.id.edit_account);
        mEditPwd = findViewById(R.id.edit_pwd);
        mTvLogin = findViewById(R.id.tv_login);

        mTvTitle.setText(mActivity.getResources().getString(R.string.example_6_validate));

        initRxjava2();
    }

    private void initRxjava2() {
        mCompositeDisposable = new CompositeDisposable();
        mAccoountPublishSubject = PublishSubject.create();
        mPwdPublishSubject = PublishSubject.create();

        Observable<Boolean> observable = Observable.combineLatest(mAccoountPublishSubject, mPwdPublishSubject,
                new BiFunction<String, String, Boolean>() {
                    @Override
                    public Boolean apply(String account, String pwd) throws Exception {
                        int nameLength = account.length();
                        int pwdLength = pwd.length();
                        return (nameLength >=3 && nameLength <=5) && (pwdLength >=6 && pwdLength <=10);
                    }
                });
        DisposableObserver<Boolean> disposableObserver = new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean){//两个输入框的内容都符合要求
                    mTvLogin.setEnabled(true);
                    mTvLogin.setBackgroundColor(Color.GREEN);
                }else {//两个输入框有不符合要求的内容
                    mTvLogin.setEnabled(false);
                    mTvLogin.setBackgroundColor(Color.GRAY);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);

    }

    @Override
    public void initListener() {
        mEditAccount.addTextChangedListener(new EditTextWatcher(mAccoountPublishSubject));
        mEditPwd.addTextChangedListener(new EditTextWatcher(mPwdPublishSubject));

        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity,"登录成功",Toast.LENGTH_SHORT).show();
            }
        });

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
    }

    /**
     * EditText的TextWatcher
     */
    public static class EditTextWatcher implements TextWatcher{

        private PublishSubject mPublishSubject;

        public EditTextWatcher(PublishSubject publishSubject){
            this.mPublishSubject = publishSubject;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mPublishSubject.onNext(s.toString());
        }
    }
}
