package com.szxb.tangren.myapplication.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.inteFace.CustomClickListener;
import com.szxb.tangren.myapplication.inteFace.CustomItemLongClickListener;

/**
 * Created by TangRen on 2016/7/6.
 */
public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView textView1, textView2, textView3, textView4,textView5;

    public Context context;

    public CustomItemLongClickListener itemLongClickListener;

    public CustomClickListener clickListener;

    public NoteViewHolder(Context context, View itemView, CustomItemLongClickListener itemLongClickListener, CustomClickListener clickListener) {
        super(itemView);
        this.context = context;
        this.itemLongClickListener = itemLongClickListener;
        this.clickListener = clickListener;
        textView1 = (TextView) itemView.findViewById(R.id.all_recy_title);
        textView2 = (TextView) itemView.findViewById(R.id.all_recy_context);
        textView3 = (TextView) itemView.findViewById(R.id.all_recy_group);
        textView4 = (TextView) itemView.findViewById(R.id.all_recy_date);
        textView5 = (TextView) itemView.findViewById(R.id.all_id);
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
        if (itemLongClickListener != null) {
            itemView.setBackgroundResource(R.drawable.recycler_bg);
            itemLongClickListener.setItemLongClick(v, getAdapterPosition());
        }
        return true;
    }
}
