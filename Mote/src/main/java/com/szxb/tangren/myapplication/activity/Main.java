package com.szxb.tangren.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.fragment.MoodFragment;
import com.szxb.tangren.myapplication.fragment.MynoteFragment;
import com.szxb.tangren.myapplication.fragment.PswFragment;
import com.szxb.tangren.myapplication.utils.CustomAddDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by TangRen on 2016/7/20.
 */
public class Main extends AppCompatActivity {

    @InjectView(R.id.mtoolbar)
    Toolbar mtoolbar;
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    @InjectView(R.id.floatAction)
    FloatingActionButton floatAction;


    private CustomAddDialog mDialog;

    private String[] title = {"我的日记", "密码本", "心情语录"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initToolbar();

        initFragment();

    }

    private void initToolbar() {
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Note");
    }

    private void initFragment() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new MynoteFragment();
                } else if (position == 1) {
                    return new PswFragment();
                } else
                    return new MoodFragment();
            }

            @Override
            public int getCount() {
                return title.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "唐人便签下载地址：" + "\n" + "http://www.wandoujia.com/apps/com.example.project/download");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到"));
                break;
            case R.id.action_export:

                break;
            case R.id.action_import:

                break;

            case R.id.action_setting:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    public void checkin(View view) {
        if (view.getId() == R.id.floatAction) {
            mDialog = new CustomAddDialog(Main.this);
            mDialog.setClickListenter(new CustomAddDialog.ClickListenerInterface() {
                @Override
                public void mark() {
                    startActivity(new Intent(Main.this, NoteActivity.class));
                    mDialog.dismiss();
                }

                @Override
                public void wen() {
                    startActivity(new Intent(Main.this, PswActivity.class));
                    mDialog.dismiss();
                }

                @Override
                public void xinqing() {
                    startActivity(new Intent(Main.this, MoodActivity.class));
                    mDialog.dismiss();
                }
            });
            mDialog.show();
        }
    }
}
