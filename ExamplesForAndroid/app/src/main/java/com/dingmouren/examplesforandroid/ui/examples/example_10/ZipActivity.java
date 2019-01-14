package com.dingmouren.examplesforandroid.ui.examples.example_10;

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
import com.dingmouren.examplesforandroid.model.ClassBean;
import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.model.Student;
import com.dingmouren.examplesforandroid.model.Teacher;
import com.dingmouren.examplesforandroid.ui.examples.example_9.NestQuestActivity;
import com.dingmouren.examplesforandroid.util.JsonUtils;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingmouren
 */
public class ZipActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    private EditText mEditGrade;

    private Button mBtn;

    private TextView mTvLog;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,ZipActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zip;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
        mEditGrade = findViewById(R.id.edit_grade);
        mBtn = findViewById(R.id.btn);
        mTvLog = findViewById(R.id.tv_log);

        mTvTitle.setText(mActivity.getResources().getString(R.string.example_10_zip));

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
        String gradeStr = mEditGrade.getText().toString().trim();

        if (TextUtils.isEmpty(gradeStr)){
            Toast.makeText(mActivity,"输入不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        int gradeInt = Integer.parseInt(gradeStr);

        Observable<MyResponse<Teacher>> observableTeacher = HttpManager.createService(Api.class).getTeacher(gradeInt);

        Observable<MyResponse<List<Student>>> observableStudents = HttpManager.createService(Api.class).getStudents(gradeInt);

        Observable.zip(observableTeacher, observableStudents,
                new BiFunction<MyResponse<Teacher>, MyResponse<List<Student>>, ClassBean>() {
                    @Override
                    public ClassBean apply(MyResponse<Teacher> teacherMyResponse, MyResponse<List<Student>> studentListMyResponse) throws Exception {

                        mainThreadTextLog("请求到得老师数据：\n"+JsonUtils.formatJson(new Gson().toJson(teacherMyResponse))+
                                "\n请求到得学生数据：\n"+JsonUtils.formatJson(new Gson().toJson(studentListMyResponse)));

                        String teacherName = teacherMyResponse.data.name;
                        String grade = teacherMyResponse.data.grade;
                        List<Student> studentList = studentListMyResponse.data;
                        ClassBean classBean = new ClassBean(teacherName,grade,studentList);
                        return classBean;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ClassBean classBean) {
                        mainThreadTextLog("onNext合并后得数据：\n"+JsonUtils.formatJson(new Gson().toJson(classBean)));
                    }

                    @Override
                    public void onError(Throwable e) {

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
