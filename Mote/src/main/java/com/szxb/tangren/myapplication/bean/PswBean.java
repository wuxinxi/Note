package com.szxb.tangren.myapplication.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by TangRen on 2016/7/22.
 */
@Table(name = "PswBean", id = "_id")
public class PswBean extends Model {

    @Column(name = "name")
    public String name;

    @Column(name = "zhanghao")
    public String zhanghao;


    @Column(name = "psw")
    public String psw;

    @Column(name = "other")
    public String other;

    @Column(name = "date")
    public String date;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getZhanghao() {
        return zhanghao;
    }

    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao;
    }
}
