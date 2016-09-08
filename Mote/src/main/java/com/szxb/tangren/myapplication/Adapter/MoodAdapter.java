package com.szxb.tangren.myapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.bean.MoodBean;
import com.szxb.tangren.myapplication.inteFace.CustomClickListener;
import com.szxb.tangren.myapplication.inteFace.CustomItemLongClickListener;
import com.szxb.tangren.myapplication.viewHolder.MoodViewHolder;

import java.util.List;

/**
 * Created by TangRen on 2016/7/25.
 */
public class MoodAdapter extends RecyclerView.Adapter<MoodViewHolder> {

    private List<MoodBean> moodBeans;

    private Context context;

    private CustomClickListener listener;

    private CustomItemLongClickListener longClickListener;

    private LinearLayout linear_layout;


    public MoodAdapter(Context context, List<MoodBean> moodBeans, LinearLayout linear_layout) {
        this.context = context;
        this.moodBeans = moodBeans;
        this.linear_layout = linear_layout;
    }

    @Override
    public MoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_cardview_mood, parent, false);
        MoodViewHolder holder = new MoodViewHolder(context, view, listener, longClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MoodViewHolder holder, int position) {
        MoodBean bean = moodBeans.get(position);
        Log.d("----------", moodBeans.size() + "");
        Log.d("----------", moodBeans.size() + "");
        Log.d("----------", moodBeans.size() + "");

        if (bean == null)
            return;


        MoodViewHolder mHolder = holder;
        bindData(bean, mHolder.id, mHolder.title, mHolder.content, mHolder.imageView, mHolder.label, mHolder.postion, mHolder.date);
    }

    private void bindData(MoodBean bean, TextView id, TextView title, TextView content, ImageView imageView, TextView label, TextView postion, TextView date) {
//
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap bitmap = BitmapFactory.decodeFile(bean.getImagepath(), options);
        imageView.setImageBitmap(bitmap);
        id.setText(bean.getId() + "");
        title.setText(bean.getTitle());
        content.setText(bean.getContent());
        label.setText(bean.getLabel());
        postion.setText(bean.getPostion());
        date.setText(bean.getDate());

    }

    @Override
    public int getItemCount() {

        if (moodBeans.size() == 0)
            linear_layout.setBackgroundResource(R.mipmap.null_record);
        else
            linear_layout.setBackgroundResource(0);
        return moodBeans.size();
    }

    public void onMove(int fromPos, int toPos) {
        MoodBean prev = moodBeans.remove(fromPos);
        moodBeans.add(toPos > fromPos ? toPos - 1 : toPos, prev);
        notifyItemMoved(fromPos, toPos);
    }

    public void setClickListener(CustomClickListener clickListener) {
        this.listener = clickListener;
    }

    public void setLongClickListener(CustomItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    public void onSwipe(RecyclerView.ViewHolder viewHolder) {
        moodBeans.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }

}
