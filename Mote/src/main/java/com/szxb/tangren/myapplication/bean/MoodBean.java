package com.szxb.tangren.myapplication.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by TangRen on 2016/7/25.
 */
@Table(name = "MoodBean", id = "_id")
public class MoodBean extends Model {

    @Column(name = "title")
    public String title;

    @Column(name = "content")
    public String content;

    @Column(name = "postion")
    public String postion;

    @Column(name = "label")
    public String label;

    @Column(name = "date")
    public String date;

    @Column(name = "imagepath")
    public String imagepath;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MoodBean()

    {
        super();
    }
}
