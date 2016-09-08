package com.szxb.tangren.myapplication.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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

/**
 * Created by TangRen on 2016/7/20.
 */
public class NoteActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private MenuItem mItem = null;

    private SildingFinishLayout layout;

    private EditText title, content;

    private String label = "生活";

    private PerformEdit per_title;

    private PerformEdit per_content;

    private Control control;

    private int flag = 0;//判断是新建还是修改,1:修改 0：新建

    private long id;//id

    private LocalBroadcastManager manager;

    private BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_write);

        initView();

        setFinish();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.mtoolbar);
        layout = (SildingFinishLayout) findViewById(R.id.layout);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.context);
        title.requestFocus();
        per_title = new PerformEdit(title);
        per_content = new PerformEdit(content);

        control = new Control(NoteActivity.this);

        mToolbar.setTitle("记笔记");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        flag = intent.getFlags();
        if (flag == 1) {
            id = intent.getLongExtra("id", 0);
            title.setText(intent.getStringExtra("title"));
            content.setText(intent.getStringExtra("content"));
        }

        layout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                NoteActivity.this.finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        });

        layout.setTouchView(layout);
    }

    private void setFinish() {
        manager = LocalBroadcastManager.getInstance(NoteActivity.this);
        IntentFilter fileFilter = new IntentFilter();
        fileFilter.addAction("exit");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        };
        manager.registerReceiver(receiver, fileFilter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
                break;
            case R.id.remoke:

                if (title.hasFocus()) {
                    per_title.undo();
                } else if (content.hasFocus()) {
                    per_content.undo();
                }

                break;
            case R.id.redo:

                if (title.hasFocus()) {
                    per_title.redo();
                } else if (content.hasFocus()) {
                    per_content.redo();
                }

                break;
            case R.id.label:
                choseItem();
                break;
            case R.id.save:
                if (flag == 0)
                    control.addNote(title, content, label);
                else
                    control.updateNote(title, content, label, id);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return super.onCreatePanelMenu(featureId, menu);
    }

    private void choseItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_loyalty_white_24dp);
        builder.setTitle("请选择您的标签：");
        final String type[] = {"生活", "学习", "工作", "其他"};
        builder.setItems(type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                label = type[which];
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
    }
}
