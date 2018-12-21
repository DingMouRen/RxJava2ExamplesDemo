package com.dingmouren.examplesforandroid.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.app.Application.ActivityLifecycleCallbacks;


import java.util.LinkedList;

/**
 * Created by dingmouren on 2018/3/22.
 * emial: naildingmouren@gmail.com
 * 应用全局级别的工具类：可以做Activity的管理等
 *
 * init              ：初始化ApplicationUtils，注册所有activity生命周期的监听
 * getApp            ：获取应用级别的上下文对象（首先要初始化）
 * finishAllActivity ：销毁所有的activity
 */

public class ApplicationUtils {

    private static Application sApplication;

    private static boolean sIsDebug = false;//当前应用是debug版本还是relesse版本

    private static LinkedList<Activity> sActivityList = new LinkedList<>();//存储activity的集合

    private static ActivityLifecycleCallbacks sActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {//监听所有activity的生命周期变化
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);//删除销毁的activity
        }
    };

    private ApplicationUtils(){ throw new UnsupportedOperationException("can't instaniate ApplicationUtils");}

    /**
     * 初始化ApplicationUtils，注册所有activity生命周期的监听
     * @param context
     */
    public static void init(final Context context){
        ApplicationUtils.sApplication = (Application) context.getApplicationContext();
        ApplicationUtils.sApplication.registerActivityLifecycleCallbacks(sActivityLifecycleCallbacks);//监听所有activity的生命周期变化
        synIsDebug(context);//判断当前是debug版本还是release版本
    }

    /**
     * 获取应用级别的上下文对象
     * @return
     */
    public static Application getApp(){
        if (null != sApplication) return sApplication;
        throw new NullPointerException("you should init first at your application");//必须在应用的application中初始化，才可以使用
    }

    /**
     * 将新创建的activity或者到可见状态的activity置于集合的队尾
     * @param activity
     */
    public static void setTopActivity(final Activity activity){
        if (sActivityList.contains(activity)){
            /*集合中含有这个activity,如果最后一个不是这个activity,就将原来的activity删除，将这个activity添加到队尾
             * 保持一个实例对象
             * */
            if (!sActivityList.getLast().equals(activity)){
                sActivityList.remove(activity);
                sActivityList.addLast(activity);
            }
        }else {//集合中不含这个activity，直接添加到队尾
            sActivityList.addLast(activity);
        }
    }

    /**
     * 销毁所有的activity
     */
    private static void finishAllActivity(){
        if (null == sActivityList) return;
        for (Activity activity : sActivityList) activity.finish();
        sActivityList.clear();
    }

    /**
     * 强制退出app
     */
    public static void exitApp(){
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有activity的集合
     * @return
     */
    public static LinkedList<Activity> getActivityList() {
        return sActivityList;
    }

    /**
     * 判断当前是debug还是release模式
     * @return
     */
    public static boolean isDebug(){
        return sIsDebug;
    }

    private static void synIsDebug(Context context){
        sIsDebug = context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

}