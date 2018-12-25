package com.dingmouren.examplesforandroid.http;

import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.model.Student;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * @author dingmouren
 */
public interface Api {

    /**
     * 获取学生信息
     * @return
     */
    @GET("/student_info")
    Observable<MyResponse<Student>> getStudentInfo();

    /**
     * 下载apk
     * @return
     */
    @Streaming
    @GET("/file/download/apk")
    Observable<ResponseBody> downloadApk();

    /**
     * 下载图片
     * @return
     */
    @Streaming
    @GET("/file/download/img")
    Observable<ResponseBody> downloadImg();
}
