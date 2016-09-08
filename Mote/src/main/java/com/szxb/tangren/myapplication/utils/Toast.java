package com.szxb.tangren.myapplication.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.szxb.tangren.myapplication.R;

/**
 * Created by TangRen on 2016/7/18.
 */
public class Toast extends android.widget.Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     * or {@link Activity} object.
     */

    private static LayoutInflater inflater;

    public Toast(Context context) {
        super(context);
    }

    /**
     * 自定义的Toast
     *
     * @param context
     * @param text
     * @param duration 消失的时间
     * @return
     */
    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast toast = new Toast(context);
        inflater = inflater.from(context);
        View view = inflater.inflate(R.layout.toast, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(text);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, formatDipToPx(context, 80));
        toast.setDuration(duration);
        return toast;
    }

    /**
     * 把dip单位转成px单位
     *
     * @param context
     * @param dip
     * @return
     */
    public static int formatDipToPx(Context context, int dip) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return (int) Math.ceil(dip * dm.density);
    }

    /**
     * 把px单位转成dip单位
     *
     * @param context
     * @param px
     * @return
     */
    public static int formatPxToDip(Context context, int px) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return (int) Math.ceil(((px * 160) / dm.densityDpi));
    }
}
