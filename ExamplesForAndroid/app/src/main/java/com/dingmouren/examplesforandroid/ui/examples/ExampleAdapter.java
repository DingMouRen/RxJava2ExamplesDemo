package com.dingmouren.examplesforandroid.ui.examples;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.model.ExampleModel;

import java.util.List;

/**
 * @author dingmouren
 */
public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private List<ExampleModel> mList;

    private Context mContext;

    private OnExampleItemClickListener mOnExampleItemClickListener;

    public ExampleAdapter(Context context,List<ExampleModel> list){
        this.mContext =  context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_example,viewGroup,false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {
        final ExampleModel bean = mList.get(position);
        if (bean == null)return;
        holder.tvIndex.setText(position + 1+"");
        holder.tvTitle.setText(bean.title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnExampleItemClickListener != null)mOnExampleItemClickListener.onExampleItemClick(position,bean.strId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle,tvIndex;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvIndex = itemView.findViewById(R.id.tv_index);
        }
    }

    public void setOnExampleItemClickListener(OnExampleItemClickListener listener){
        this.mOnExampleItemClickListener = listener;
    }

    public interface OnExampleItemClickListener{
        void onExampleItemClick(int position,int strId);
    }
}
