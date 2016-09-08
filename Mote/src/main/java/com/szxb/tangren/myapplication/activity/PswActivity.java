package com.szxb.tangren.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.utils.Control;
import com.szxb.tangren.myapplication.utils.PerformEdit;
import com.szxb.tangren.myapplication.utils.SildingFinishLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by TangRen on 2016/7/22.
 */
public class PswActivity extends AppCompatActivity  {

    @InjectView(R.id.mtoolbar)
    Toolbar mtoolbar;
    @InjectView(R.id.name)
    EditText name;
    @InjectView(R.id.psw)
    EditText psw;
    @InjectView(R.id.content)
    EditText content;
    @InjectView(R.id.layout)
    SildingFinishLayout layout;
    @InjectView(R.id.zhanghao)
    EditText zhanghao;

    private PerformEdit per_name;

    private PerformEdit per_zhanghao;

    private PerformEdit per_psw;

    private PerformEdit per_content;

    private Control control;

    private int flag = 0;//判断是新建还是修改,1:修改 0：新建

    private long id;//id

    private LocalBroadcastManager manager;

    private BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psw_write);
        ButterKnife.inject(this);

        init();
        initFish();
    }

    private void initFish() {
        manager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("exit_psw");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        };
        manager.registerReceiver(receiver, filter);
    }

    private void init() {
        mtoolbar.setTitle("密码录");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        per_name = new PerformEdit(name);
        per_zhanghao = new PerformEdit(zhanghao);
        per_psw = new PerformEdit(psw);
        per_content = new PerformEdit(content);

        layout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                PswActivity.this.finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        });

        layout.setTouchView(layout);

        control = new Control(PswActivity.this);


        Intent intent = getIntent();
        flag = intent.getFlags();
        if (flag == 1) {
            id = intent.getLongExtra("id", 0);
            name.setText(intent.getStringExtra("name"));
            zhanghao.setText(intent.getStringExtra("zhanghao"));
            psw.setText(intent.getStringExtra("psw"));
            content.setText(intent.getStringExtra("content"));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.psw_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);

                break;
            case R.id.remoke://重做

                if (name.hasFocus()) per_name.undo();
                else if (psw.hasFocus()) per_psw.undo();
                else if (zhanghao.hasFocus()) per_zhanghao.undo();
                else if (content.hasFocus()) per_content.undo();

                break;
            case R.id.redo://撤销

                if (name.hasFocus()) per_name.redo();
                else if (zhanghao.hasFocus()) per_zhanghao.redo();
                else if (psw.hasFocus()) per_psw.redo();
                else if (content.hasFocus()) per_content.redo();

                break;
            case R.id.save:
                if (flag == 0)
                    control.addPsw(name, zhanghao, psw, content);
                else
                    control.update_psw(name, zhanghao, psw, content, id);

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
    }
}
