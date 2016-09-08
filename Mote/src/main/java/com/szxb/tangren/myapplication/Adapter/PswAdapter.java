package com.szxb.tangren.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.bean.PswBean;
import com.szxb.tangren.myapplication.inteFace.CustomClickListener;
import com.szxb.tangren.myapplication.inteFace.CustomItemLongClickListener;
import com.szxb.tangren.myapplication.viewHolder.PswViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangRen on 2016/7/6.
 */
public class PswAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;

    public List<PswBean> list_beans = new ArrayList<PswBean>();

    public CustomClickListener clickListener;

    public CustomItemLongClickListener itemLongClickListener;

    private LinearLayout linearLayout;

    public PswAdapter(Context context, List<PswBean> list_beans, LinearLayout linearLayout) {
        this.context = context;
        this.list_beans = list_beans;
        this.linearLayout = linearLayout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_cardview_psw, parent, false);
        PswViewHolder holder = new PswViewHolder(context, view, itemLongClickListener, clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PswBean bean = list_beans.get(position);
        Log.d("----------", list_beans.size() + "");

        if (bean == null)
            return;
        PswViewHolder mHolder = (PswViewHolder) holder;
        binData(bean, mHolder.name, mHolder.zhanghao, mHolder.psw, mHolder.content, mHolder.date, mHolder.id);

    }

    private void binData(PswBean bean, TextView textView1, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView id) {
        textView1.setText(bean.getName());
        textView.setText(bean.getZhanghao());
        textView2.setText(bean.getPsw());
        textView3.setText(bean.getOther());
        textView4.setText(bean.getDate());
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
        PswBean prev = list_beans.remove(fromPos);
        list_beans.add(toPos > fromPos ? toPos - 1 : toPos, prev);
        notifyItemMoved(fromPos, toPos);
    }

    public void onSwipe(RecyclerView.ViewHolder viewHolder) {
        list_beans.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }


}
