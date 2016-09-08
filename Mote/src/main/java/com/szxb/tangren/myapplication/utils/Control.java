package com.szxb.tangren.myapplication.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.EditText;

import com.szxb.tangren.myapplication.db.DBManager;
import com.szxb.tangren.myapplication.db.MoodDBManager;
import com.szxb.tangren.myapplication.db.Psw_DBManager;

/**
 * Created by TangRen on 2016/7/22.
 */
public class Control {
    private Context context;

    private DBManager manager;

    private Psw_DBManager psw_dbManager;

    private MoodDBManager moodManager;

    public Control(Context content) {
        this.context = content;
        manager = new DBManager();
        psw_dbManager = new Psw_DBManager();
        moodManager = new MoodDBManager();
    }

    public void addNote(EditText title, EditText content, String lable) {
        String t = title.getText().toString();
        String c = content.getText().toString();
        if (t.equals("") || c.equals("")) {
            Toast.makeText(context, "标题或内容不能缺省哦！", Toast.LENGTH_SHORT).show();
        } else {
            manager.addNote(t, c, lable, Utils.getDate());
            sendBroadcast_finish();
            sendBroadcast();
        }
    }

    public void updateNote(EditText title, EditText content, String lable, long id) {
        String t = title.getText().toString();
        String c = content.getText().toString();
        if (t.equals("") || c.equals("")) {
            Toast.makeText(context, "标题或内容不能缺省哦！", Toast.LENGTH_SHORT).show();
        } else {
            manager.updateNote(id, t, c, lable, Utils.getDate());
            sendBroadcast_finish();
            sendBroadcast();

        }
    }

    public void addPsw(EditText name, EditText zhanghao, EditText psw, EditText content) {
        String t = name.getText().toString();
        String z = zhanghao.getText().toString();
        String p = psw.getText().toString();
        String c = content.getText().toString();
        if (t.equals("") || p.equals("") || z.equals("")) {
            Toast.makeText(context, "标题、账号或密码不能缺省哦！", Toast.LENGTH_SHORT).show();
        } else {
            psw_dbManager.addPsw(t, z, p, c, Utils.getDate());
            sendBroadcast_finish_psw();
            sendBroadcast_psw();

        }
    }

    public void update_psw(EditText name, EditText zhanghao, EditText psw, EditText content, long id) {
        String n = name.getText().toString();
        String z = zhanghao.getText().toString();
        String p = psw.getText().toString();
        String c = content.getText().toString();
        if (n.equals("") || p.equals("") || z.equals("")) {
            Toast.makeText(context, "标题、账户或密码不能缺省哦！", Toast.LENGTH_SHORT).show();
        } else {
            psw_dbManager.updatPsw(id, n, z, p, c, Utils.getDate());
            sendBroadcast_finish_psw();
            sendBroadcast_psw();
        }
    }

    public void addMood(EditText title, EditText content, String imagePath, String label, String postion) {
        String t = title.getText().toString();
        String c = content.getText().toString();

        if (t.equals("") || content.equals("")) {
            Toast.makeText(context, "标题或内容不能缺省哦！", Toast.LENGTH_SHORT).show();
        } else {
            moodManager.addMood(t, c, postion, label, imagePath, Utils.getDate());
            sendBroadcast_finish_mood();
            sendBroadcast_mood();
        }

    }


    public void updatMood(long id, EditText title, EditText content, String labelString, String postionString, String imagePath) {
        String t = title.getText().toString();
        String c = content.getText().toString();

        if (t.equals("") || content.equals("")) {
            Toast.makeText(context, "标题或内容不能缺省哦！", Toast.LENGTH_SHORT).show();
        } else {
            moodManager.update(id, t, c, postionString, labelString, imagePath, Utils.getDate());
            sendBroadcast_finish_mood();
            sendBroadcast_mood();
        }

    }


    //完成日记退出这个activity

    private void sendBroadcast_finish_mood() {
        Intent intent = new Intent();
        intent.setAction("exit_mood");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendBroadcast_finish_psw() {
        Intent intent = new Intent();
        intent.setAction("exit_psw");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendBroadcast_finish() {
        Intent intent = new Intent();
        intent.setAction("exit");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    //修改或新建完成后自动刷新
    private void sendBroadcast_mood() {
        Intent intent = new Intent();
        intent.setAction("refresh_mood");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendBroadcast_psw() {
        Intent intent = new Intent();
        intent.setAction("refresh_psw");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction("refresh");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    //复制文字

    public void copy(String content) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setText(content);
    }


}
