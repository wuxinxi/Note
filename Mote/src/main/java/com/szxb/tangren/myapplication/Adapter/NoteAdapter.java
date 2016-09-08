package com.szxb.tangren.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.bean.NoteBean;
import com.szxb.tangren.myapplication.inteFace.CustomClickListener;
import com.szxb.tangren.myapplication.inteFace.CustomItemLongClickListener;
import com.szxb.tangren.myapplication.viewHolder.NoteViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangRen on 2016/7/6.
 */
public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<NoteBean> list_beans = new ArrayList<NoteBean>();

    private CustomClickListener clickListener;

    private CustomItemLongClickListener itemLongClickListener;

    private LinearLayout linearLayout;

    public NoteAdapter(Context context, List<NoteBean> list_beans, LinearLayout linearLayout) {
        this.context = context;
        this.list_beans = list_beans;
        this.linearLayout = linearLayout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_cardview, parent, false);
        NoteViewHolder holder = new NoteViewHolder(context, view, itemLongClickListener, clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoteBean bean = list_beans.get(position);
        if (bean == null)
            return;
        NoteViewHolder mHolder = (NoteViewHolder) holder;
        binData(bean, mHolder.textView1, mHolder.textView2, mHolder.textView3, mHolder.textView4, mHolder.textView5);
    }

    private void binData(NoteBean bean, TextView textView1, TextView textView2, TextView textView3, TextView textView4, TextView id) {
        textView1.setText(bean.getN_title());
        textView2.setText(bean.getN_content());
        textView3.setText(bean.getN_label());
        textView4.setText(bean.getN_date());
        id.setText(bean.getId() + "");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (list_beans.size() == 0) linearLayout.setBackgroundResource(R.mipmap.null_record);
        else linearLayout.setBackgroundResource(0);
        return list_beans.size() == 0 ? 0 : list_beans.size();
    }

    public void setItemClickListener(CustomClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setItemLongClickListener(CustomItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    //删除item
    public void onMove(int fromPos, int toPos) {
        NoteBean prev = list_beans.remove(fromPos);
        list_beans.add(toPos > fromPos ? toPos - 1 : toPos, prev);
        notifyItemMoved(fromPos, toPos);
    }

    public void onSwipe(RecyclerView.ViewHolder viewHolder) {
        list_beans.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }


}
