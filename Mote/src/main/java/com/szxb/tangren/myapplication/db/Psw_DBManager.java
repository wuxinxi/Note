package com.szxb.tangren.myapplication.db;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.szxb.tangren.myapplication.bean.PswBean;

import java.util.List;


/**
 * Created by TangRen on 2016/7/22.
 */
public class Psw_DBManager {

    //查询密码录
    public static List<PswBean> query() {

        return new Select()
                .from(PswBean.class)
                .orderBy("_id desc")
                .execute();
    }

    //删除单个密码录
    public static void delete(long id) {
        new Delete().from(PswBean.class).where("_id=?", id).execute();
    }

    //清除所有密码录
    public static void deleteAll() {
        new Delete().from(PswBean.class).execute();
    }

    //添加密码录
    public static void addPsw(String name, String zhanghao, String psw, String content, String time) {
        PswBean bean = new PswBean();
        bean.name = name;
        bean.zhanghao = zhanghao;
        bean.psw = psw;
        bean.other = content;
        bean.date = time;
        bean.save();
    }

    //修改密码录
    public static void updatPsw(long id, String name, String zhanghao, String psw, String content, String time) {
        new Update(PswBean.class)
                .set("name=?,zhanghao=?,psw=?,other=?,date=?", name, zhanghao, psw, content, time)
                .where("_id=?", id)
                .execute();
    }


}
