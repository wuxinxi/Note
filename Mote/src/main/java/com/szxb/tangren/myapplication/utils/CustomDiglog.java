package com.szxb.tangren.myapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.szxb.tangren.myapplication.R;


/**
 * Created by TangRen on 2016-05-30.
 */

public class CustomDiglog extends Dialog {
    private Context mContext;
    private TextView del, edi,  copy, share;
    private ClickListenerInterface clickListenerInterface;


    public interface ClickListenerInterface {
        public void del();

        public void edit();

        public void copy();

        public void share();
    }

    public CustomDiglog(Context context) {
        super(context, R.style.bottomDialogStyle);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_dialog_all, null);
        del = (TextView) view.findViewById(R.id.del);
        edi = (TextView) view.findViewById(R.id.edit);
        copy = (TextView) view.findViewById(R.id.copy);
        share = (TextView) view.findViewById(R.id.share);

        del.setOnClickListener(new clickListener());
        edi.setOnClickListener(new clickListener());
        copy.setOnClickListener(new clickListener());
        share.setOnClickListener(new clickListener());

        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
//        lp.height = (int) (d.heightPixels * 0.4);
        dialogWindow.setAttributes(lp);
    }

    public void setClickListenter(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.del:
                    clickListenerInterface.del();
                    break;
                case R.id.edit:
                    clickListenerInterface.edit();
                    break;
                case R.id.copy:
                    clickListenerInterface.copy();
                    break;
                case R.id.share:
                    clickListenerInterface.share();
                    break;

            }
        }
    }
}
