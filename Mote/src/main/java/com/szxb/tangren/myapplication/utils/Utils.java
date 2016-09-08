package com.szxb.tangren.myapplication.utils;

import android.app.Activity;
import android.content.Intent;

import com.szxb.tangren.myapplication.activity.MoodActivity;
import com.szxb.tangren.myapplication.activity.NoteActivity;
import com.szxb.tangren.myapplication.activity.PswActivity;
import com.szxb.tangren.myapplication.bean.MoodBean;
import com.szxb.tangren.myapplication.bean.NoteBean;
import com.szxb.tangren.myapplication.bean.PswBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by TangRen on 2016/7/22.
 */
public class Utils {

    /**
     * 获得系统时间，即保存日记的时间
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    /**
     * 跳转到NoteActivity
     *
     * @param list
     * @param postion
     * @param context
     */
    public static void startActivity(List<NoteBean> list, int postion, Activity context) {
        Intent intent = new Intent(context, NoteActivity.class);
        intent.addFlags(1);
        intent.putExtra("id", list.get(postion).getId());
        intent.putExtra("title", list.get(postion).getN_title());
        intent.putExtra("content", list.get(postion).getN_content());
        context.startActivity(intent);
    }

    /**
     * 跳转到PswActivity
     *
     * @param list
     * @param postion
     * @param context
     */
    public static void startActivity_psw(List<PswBean> list, int postion, Activity context) {
        Intent intent = new Intent(context, PswActivity.class);
        intent.addFlags(1);
        intent.putExtra("id", list.get(postion).getId());
        intent.putExtra("name", list.get(postion).getName());
        intent.putExtra("zhanghao", list.get(postion).getZhanghao());
        intent.putExtra("psw", list.get(postion).getPsw());
        intent.putExtra("content", list.get(postion).getOther());
        context.startActivity(intent);
    }

    public static void startActivity_mood(List<MoodBean> list, int postion, Activity context) {
        Intent intent = new Intent(context, MoodActivity.class);
        intent.addFlags(1);
        intent.putExtra("id", list.get(postion).getId());
        intent.putExtra("title", list.get(postion).getTitle());
        intent.putExtra("content", list.get(postion).getContent());
        intent.putExtra("postion", list.get(postion).getPostion());
        intent.putExtra("label", list.get(postion).getLabel());
        intent.putExtra("imagepath", list.get(postion).getImagepath());
        context.startActivity(intent);
    }


}
