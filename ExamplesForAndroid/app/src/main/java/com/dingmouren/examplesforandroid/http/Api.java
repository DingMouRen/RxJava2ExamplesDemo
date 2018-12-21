package com.dingmouren.examplesforandroid.http;

import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.model.Student;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author dingmouren
 */
public interface Api {

    @GET("/student_info")
    Observable<MyResponse<Student>> getStudentInfo();
}
