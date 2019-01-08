package com.dingmouren.examplesforandroid.http;

import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.model.Student;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
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

    /**
     * 搜索联想优化
     * @param query
     * @return
     */
    @GET("/search")
    Observable<MyResponse<String>> search(@Query("query") String query);

    /**
     * 轮询操作
     * @return
     */
    @GET("/polling")
    Observable<MyResponse<String>> polling();
}
