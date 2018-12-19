package com.dingmouren.examplesforandroid.model;

import android.content.Context;

import java.io.Serializable;

/**
 * @author dingmouren
 * 操作符的名称和描述
 */
public class OperatorModel implements Serializable {
    public String operatorName;
    public String operatorDesc;
    public int operatorId;
    public OperatorModel(Context context,int nameStringId,int descStringId){
        this.operatorName = context.getString(nameStringId);
        this.operatorDesc = context.getString(descStringId);
        this.operatorId = nameStringId;
    }
}
