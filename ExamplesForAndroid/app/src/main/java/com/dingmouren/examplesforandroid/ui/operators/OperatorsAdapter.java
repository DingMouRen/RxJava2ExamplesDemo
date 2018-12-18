package com.dingmouren.examplesforandroid.ui.operators;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import java.util.List;

/**
 * @author dingmouren
 */
public class OperatorsAdapter extends RecyclerView.Adapter<OperatorsAdapter.OperatorViewHolder> {

    private List<OperatorModel> mList ;

    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public OperatorsAdapter(Context context,List<OperatorModel> list){
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public OperatorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_operator,viewGroup,false);
        return new OperatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperatorViewHolder holder, final int position) {

        final OperatorModel bean = mList.get(position);

        holder.tvOperatorName.setText(bean.operatorName);
        holder.tvOperatorDesc.setText(bean.operatorDesc);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(bean,position);
            }
        });

    }



    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class OperatorViewHolder extends RecyclerView.ViewHolder{

        TextView tvOperatorName;

        TextView tvOperatorDesc;

        public OperatorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOperatorName = itemView.findViewById(R.id.tv_operator_name);
            tvOperatorDesc = itemView.findViewById(R.id.tv_operator_desc);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{

        void onItemClick(OperatorModel model,int position);
    }
}
