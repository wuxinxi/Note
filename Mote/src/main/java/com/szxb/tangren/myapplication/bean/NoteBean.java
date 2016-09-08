package com.szxb.tangren.myapplication.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by TangRen on 2016/7/6.
 */

@Table(name = "NoteBean", id = "_id")
public class NoteBean extends Model {

    @Column(name = "title")
    public String n_title;

    @Column(name = "date")
    public String n_date;

    @Column(name = "content")
    public String n_content;

    @Column(name = "label")
    public String n_label;


    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public String getN_date() {
        return n_date;
    }

    public void setN_date(String n_date) {
        this.n_date = n_date;
    }

    public String getN_content() {
        return n_content;
    }

    public void setN_content(String n_content) {
        this.n_content = n_content;
    }

    public String getN_label() {
        return n_label;
    }

    public void setN_label(String n_label) {
        this.n_label = n_label;
    }
}
