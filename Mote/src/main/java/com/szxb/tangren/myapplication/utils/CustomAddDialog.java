package com.szxb.tangren.myapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.szxb.tangren.myapplication.R;


/**
 * Created by TangRen on 2016/6/12.
 */
public class CustomAddDialog extends Dialog {

    private Context mContext;

    private LinearLayout markDown, wenben, xinqing;

    private ImageView image_mark, image_fab,image_xinqing;

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        public void mark();

        public void wen();

        public void xinqing();

    }

    ;

    public CustomAddDialog(Context context) {
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
        View view = inflater.inflate(R.layout.item_dialog_add, null);
        markDown = (LinearLayout) view.findViewById(R.id.markdown);
        wenben = (LinearLayout) view.findViewById(R.id.wenben);
        xinqing = (LinearLayout) view.findViewById(R.id.xinqing);

        image_fab = (ImageView) view.findViewById(R.id.image_fab);
        image_mark = (ImageView) view.findViewById(R.id.image_markdown);
        image_xinqing = (ImageView) view.findViewById(R.id.xinqing_iamg);

        image_mark.setColorFilter(R.color.colorDialog, PorterDuff.Mode.MULTIPLY);
        image_fab.setColorFilter(R.color.colorDialog, PorterDuff.Mode.MULTIPLY);
        image_xinqing.setColorFilter(R.color.colorDialog, PorterDuff.Mode.MULTIPLY);

        markDown.setOnClickListener(new clickListener());
        wenben.setOnClickListener(new clickListener());
        xinqing.setOnClickListener(new clickListener());

        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.5); // 高度设置为屏幕的0.6
        lp.x = 16;
        lp.y = 20;
        dialogWindow.setGravity(Gravity.END | Gravity.BOTTOM);
        lp.height = (int) (d.heightPixels * 0.2);
        dialogWindow.setAttributes(lp);

    }

    public void setClickListenter(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.markdown:
                    clickListenerInterface.mark();
                    break;
                case R.id.wenben:
                    clickListenerInterface.wen();
                    break;
                case R.id.xinqing:
                    clickListenerInterface.xinqing();
                    break;

            }
        }
    }
}
