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

import com.szxb.tangren.myapplication.R;

import java.util.List;

/**
 * Created by TangRen on 2016/8/13.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context;
    private List<String> pathSting;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, List<String> pathSting) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.pathSting = pathSting;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.image_item, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder mHolder = holder;
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=1;
        Bitmap bitmap=BitmapFactory.decodeFile(pathSting.get(position),options);
        if (bitmap!=null)
        mHolder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        Log.d("-----------",pathSting.size()+"");
        return pathSting.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
