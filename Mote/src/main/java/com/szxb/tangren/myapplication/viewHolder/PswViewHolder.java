package com.szxb.tangren.myapplication.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.inteFace.CustomClickListener;
import com.szxb.tangren.myapplication.inteFace.CustomItemLongClickListener;

/**
 * Created by TangRen on 2016/7/22.
 */
public class PswViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView name, zhanghao,psw, content, date,id;

    public Context context;

    public CustomItemLongClickListener longClickListener;

    public CustomClickListener clickListener;


    public PswViewHolder(Context context, View itemView, CustomItemLongClickListener longClickListener, CustomClickListener clickListener) {
        super(itemView);
        this.context = context;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
        name = (TextView) itemView.findViewById(R.id.all_recy_name);
        zhanghao = (TextView) itemView.findViewById(R.id.all_recy_zhanghao);
        psw = (TextView) itemView.findViewById(R.id.all_recy_psw);
        content = (TextView) itemView.findViewById(R.id.all_recy_content);
        date = (TextView) itemView.findViewById(R.id.all_date);
        id= (TextView) itemView.findViewById(R.id.all_id);
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            itemView.setBackgroundResource(R.drawable.recycler_bg);
            clickListener.setClickListtener(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickListener != null) {
            itemView.setBackgroundResource(R.drawable.recycler_bg);
            longClickListener.setItemLongClick(v, getAdapterPosition());
        }
        return true;
    }
}
