package com.dingmouren.examplesforandroid.model;

import android.content.Context;

/**
 * @author dingmouren
 * 操作符的名称和描述
 */
public class OperatorModel {
    public String operatorName;
    public String operatorDesc;
    public OperatorModel(Context context,int nameStringId,int descStringId){
        this.operatorName = context.getString(nameStringId);
        this.operatorDesc = context.getString(descStringId);
    }
}
