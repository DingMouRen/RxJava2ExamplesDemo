package com.dingmouren.examplesforandroid.http.download;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author dingmouren
 */
public class FileDownloadObserver<T> implements Observer<T> {

    private FileCallBack fileCallBack;

    public FileDownloadObserver(FileCallBack fileCallBack){
        this.fileCallBack = fileCallBack;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        if (fileCallBack != null) fileCallBack.onStart(disposable);
    }

    @Override
    public void onNext(T t) {
        if (fileCallBack != null) fileCallBack.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (fileCallBack != null) fileCallBack.onError(e);
    }

    @Override
    public void onComplete() {
        if (fileCallBack != null) fileCallBack.onCompleted();
    }
}
