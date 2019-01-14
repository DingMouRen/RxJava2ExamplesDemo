package com.dingmouren.examplesforandroid.http;

import com.dingmouren.examplesforandroid.model.CacheToNetData;
import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.model.Nest1Bean;
import com.dingmouren.examplesforandroid.model.Nest2Bean;
import com.dingmouren.examplesforandroid.model.Student;
import com.dingmouren.examplesforandroid.model.Teacher;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
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

    /**
     * 错误重试
     * @return
     */
    @GET("/retry")
    Observable<MyResponse<String>> retry();

    /**
     * 优先加载缓存，同时发起请求
     */
    @GET("/net/data")
    Observable<MyResponse<List<CacheToNetData>>> getNetData(@Query("delayTime") long delayTime);

    /**
     * 嵌套网络请求：获取第一道门得密码
     */
    @GET("/nest/1")
    Observable<MyResponse<Nest1Bean>> openFirstDoor(@Query("pwd") int firstDoorPwd);

    /**
     * 嵌套网络请求：获取第二道门得密码，并获取宝藏
     */
    @GET("/nest/2")
    Observable<MyResponse<Nest2Bean>> openSecondDoor(@Query("pwd") int secondDoorPwd);

    /**
     * 获取指定年级得老师
     */
    @GET("/teacher")
    Observable<MyResponse<Teacher>> getTeacher(@Query("grade") int grade);

    /**
     * 获取指定年级得学生们
     */
    @GET("/students")
    Observable<MyResponse<List<Student>>> getStudents(@Query("grade") int grade);

}
