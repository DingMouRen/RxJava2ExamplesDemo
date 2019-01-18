package com.dingmouren.examplesforandroid.ui.examples.example_1.download;

import android.util.Log;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by miya95 on 2016/12/5.
 */
public abstract class FileCallBack<T> {

    private String destFileDir;
    private String destFileName;

    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        EventBus.getDefault().register(this);
    }

    /**
     * 开始下载文件
     * @param disposable
     */
    public abstract void onStart(Disposable disposable);

    /**
     * 成功返回，对应onNext函数
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 文件下载进度
     * @param progress
     * @param total
     */
    public abstract void progress(long progress, long total);

    /**
     * 文件下载完成
     */
    public abstract void onCompleted();

    /**
     * 文件下载出错
     * @param e
     */
    public abstract void onError(Throwable e);

    /**
     * 保存文件
     * @param body
     */
    public void saveFile(ResponseBody body) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            unsubscribe();
            //onCompleted();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }

    /**
     * 注销EventBus
     */
    private void unsubscribe() {
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceive(FileDownloadEvent event){
        progress(event.progress,event.total);
    }

}
