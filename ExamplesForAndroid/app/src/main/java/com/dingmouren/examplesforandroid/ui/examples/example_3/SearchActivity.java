package com.dingmouren.examplesforandroid.ui.examples.example_3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;

/**
 * 应用场景：
 * 客户端通过EditText的addTextChangedListener方法监听输入框的变化，
 * 当输入框发生变化之后就会回调afterTextChanged方法，客户端利用当前输入框内的文字向服务器发起请求，
 * 服务器返回与该搜索文字关联的结果给客户端进行展示。
 *
 * 问题：
 * 1.在用户连续输入的情况下，可能会发起某些不必要的请求。例如用户输入了abc，那么按照上面的实现，客户端就会发起a、ab、abc三个请求。
 * 2.当搜索词为空时，不应该发起请求。
 * 3.如果用户依次输入了ab和abc，那么首先会发起关键词为ab请求，之后再发起abc的请求，但是abc的请求如果先于ab的请求返回，
 * 那么就会造成用户期望搜索的结果为abc，最终展现的结果却是和ab关联的。
 *
 * 解决方案：
 * 1.使用debounce操作符，当输入框发生变化时，不会立刻将事件发送给下游，而是等待200ms，如果在这段事件内，输入框没有发生变化，那么才发送该事件；
 * 反之，则在收到新的关键词后，继续等待200ms。
 * 2.使用filter操作符，只有关键词的长度大于0时才发送事件给下游。
 * 3.使用switchMap操作符，这样当发起了abc的请求之后，即使ab的结果返回了，也不会发送给下游，从而避免了出现前面介绍的搜索词和联想结果不匹配的问题

 * @author dingmouren
 */
public class SearchActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,SearchActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_3_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);

        mTvTitle.setText(getResources().getString(R.string.example_3_search));
    }
}
