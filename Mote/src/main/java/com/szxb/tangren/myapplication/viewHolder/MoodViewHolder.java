package com.szxb.tangren.myapplication.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.inteFace.CustomClickListener;
import com.szxb.tangren.myapplication.inteFace.CustomItemLongClickListener;

/**
 * Created by TangRen on 2016/7/25.
 */
public class MoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private Context context;

    public TextView title;

    public TextView id;

    public TextView content;

    public TextView label;

    public TextView date;

    public ImageView imageView;

    public TextView postion;

    private CustomClickListener listener;

    private CustomItemLongClickListener longClickListener;

    public MoodViewHolder(Context context, View itemView, CustomClickListener listener, CustomItemLongClickListener longClickListener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        this.longClickListener = longClickListener;
        title = (TextView) itemView.findViewById(R.id.all_recy_title);
        id = (TextView) itemView.findViewById(R.id.all_id);
        content = (TextView) itemView.findViewById(R.id.all_recy_content);
        label = (TextView) itemView.findViewById(R.id.all_recy_group);
        date = (TextView) itemView.findViewById(R.id.all_recy_date);
        postion = (TextView) itemView.findViewById(R.id.all_recy_postion);

        imageView = (ImageView) itemView.findViewById(R.id.iamge);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (listener != null) {
            itemView.setBackgroundResource(R.drawable.recycler_bg);
            listener.setClickListtener(v, getAdapterPosition());
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
