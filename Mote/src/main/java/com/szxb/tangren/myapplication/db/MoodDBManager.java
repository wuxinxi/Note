package com.szxb.tangren.myapplication.db;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.szxb.tangren.myapplication.bean.MoodBean;

import java.util.List;

/**
 * Created by TangRen on 2016/7/25.
 */
public class MoodDBManager {

    public static List<MoodBean> query() {
        return new Select().from(MoodBean.class)
                .orderBy("_id desc")
                .execute();
    }

    public static void addMood(String title, String content, String postion, String label, String imagePath, String date) {
        MoodBean moodBean = new MoodBean();
        moodBean.title = title;
        moodBean.content = content;
        moodBean.postion = postion;
        moodBean.label = label;
        moodBean.imagepath = imagePath;
        moodBean.date = date;

        moodBean.save();
    }


    public static void del(long id) {
        new Delete().from(MoodBean.class).where("_id=?", id).execute();
    }


    public static void update(long id, String title, String content, String postion, String label, String path, String date) {
        if (path==null){
            new Update(MoodBean.class)
                    .set("title=?,content=?,postion=?,label=?,date=?", title, content, postion, label, date)
                    .where("_id=?", id)
                    .execute();
        }else {
            new Update(MoodBean.class)
                    .set("title=?,content=?,postion=?,label=?,imagepath=?,date=?", title, content, postion, label, path, date)
                    .where("_id=?", id)
                    .execute();
        }

    }
}
