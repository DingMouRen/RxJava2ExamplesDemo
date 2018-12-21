package com.dingmouren.examplesforandroid.model;

import android.content.Context;

/**
 * @author dingmouren
 */
public class ExampleModel {
    public String title;
    public ExampleModel(Context context,int titleID){
        this.title = context.getResources().getString(titleID);
    }
}
