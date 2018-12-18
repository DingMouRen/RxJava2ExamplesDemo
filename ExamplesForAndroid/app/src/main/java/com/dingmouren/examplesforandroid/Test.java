package com.dingmouren.examplesforandroid;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author dingmouren
 */
public class Test {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3};
        Observable.just(arr)
                .subscribe(new Observer<int[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(int[] ints) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
