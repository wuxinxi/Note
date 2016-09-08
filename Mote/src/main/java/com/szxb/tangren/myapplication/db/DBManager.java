package com.szxb.tangren.myapplication.db;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.szxb.tangren.myapplication.bean.NoteBean;

import java.util.List;

/**
 * Created by TangRen on 2016/7/22.
 */
public class DBManager {

    //查询日记
    public static List<NoteBean> query() {

        return new Select()
                .from(NoteBean.class)
                .orderBy("_id desc")
                .execute();
    }

    //删除单个日记
    public static void delete(long id) {
        new Delete().from(NoteBean.class).where("_id=?", id).execute();
    }

    //清除所有日记
    public static void deleteAll() {
        new Delete().from(NoteBean.class).execute();
    }

    //添加日记
    public static void addNote(String title, String content, String label, String time) {
        NoteBean bean = new NoteBean();
        bean.n_title = title;
        bean.n_content = content;
        bean.n_label = label;
        bean.n_date = time;
        bean.save();
    }

    //修改日记
    public static void updateNote(long id, String title, String content, String lable, String time) {
        new Update(NoteBean.class)
                .set("title=?,content=?,label=?,date=?", title, content, lable, time)
                .where("_id=?", id)
                .execute();
    }


}
