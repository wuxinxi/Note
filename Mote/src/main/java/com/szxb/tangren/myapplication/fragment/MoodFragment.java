package com.szxb.tangren.myapplication.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szxb.tangren.myapplication.Adapter.MoodAdapter;
import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.bean.MoodBean;
import com.szxb.tangren.myapplication.db.MoodDBManager;
import com.szxb.tangren.myapplication.inteFace.CustomClickListener;
import com.szxb.tangren.myapplication.inteFace.CustomItemLongClickListener;
import com.szxb.tangren.myapplication.utils.Control;
import com.szxb.tangren.myapplication.utils.CustomDiglog;
import com.szxb.tangren.myapplication.utils.Toast;
import com.szxb.tangren.myapplication.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangRen on 2016/7/20.
 */
public class MoodFragment extends Fragment implements CustomClickListener, CustomItemLongClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View rootView;

    private List<MoodBean> moodBeans = new ArrayList<MoodBean>();

    private static SwipeRefreshLayout refreshLayout;

    private RecyclerView recyclerView;

    private static MoodAdapter mAdapter;

    private CustomDiglog mDialog;

    private Control control;

    private LinearLayout linear_layout;

    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 1:
                    mAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.main_main, null);
            initView();

            initData();
            startRefresh();
        }
        ViewGroup group = (ViewGroup) rootView.getParent();
        if (group != null) {
            group.removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.reFreshLayout);
        linear_layout= (LinearLayout) rootView.findViewById(R.id.linear_layout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mAdapter = new MoodAdapter(getActivity(), moodBeans,linear_layout);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setClickListener(this);
        mAdapter.setLongClickListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);

        control = new Control(getActivity());


        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                mAdapter.onMove(fromPos, toPos);
                Toast.makeText(getActivity(), "MOVE", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int postion = viewHolder.getLayoutPosition();
                long id = moodBeans.get(postion).getId();
                MoodDBManager.del(id);
                mAdapter.onSwipe(viewHolder);
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initData() {
        if (moodBeans.size() > 0)
            moodBeans.clear();
        try {
            moodBeans.addAll(MoodDBManager.query());
        } catch (Exception e) {

            e.toString();
        }

        handler.sendEmptyMessage(1);
    }


    private void startRefresh() {

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter filter = new IntentFilter();
        filter.addAction("refresh_mood");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(true);
                        initData();
                    }
                });
            }
        };

        manager.registerReceiver(receiver, filter);

    }

    @Override
    public void setClickListtener(View view, int postion) {
        Utils.startActivity_mood(moodBeans, postion, getActivity());
    }

    @Override
    public void setItemLongClick(View view, final int postion) {
        mDialog = new CustomDiglog(getActivity());
        mDialog.setClickListenter(new CustomDiglog.ClickListenerInterface() {
            @Override
            public void del() {

                MoodDBManager.del(moodBeans.get(postion).getId());
                moodBeans.remove(postion);
                handler.sendEmptyMessage(1);

                mDialog.dismiss();
            }

            @Override
            public void edit() {
                Utils.startActivity_mood(moodBeans, postion, getActivity());
            }

            @Override
            public void copy() {
                control.copy("标题：" + moodBeans.get(postion).getTitle()
                        + "\n" + "心情：" + moodBeans.get(postion).getContent()
                        + "\n" + "位置:" + moodBeans.get(postion).getPostion()
                        + "\n" + Utils.getDate());
                mDialog.dismiss();
            }

            @Override
            public void share() {
                Intent intent = new Intent(Intent.ACTION_SEND);

                if (moodBeans.get(postion).getImagepath() == null) {
                    intent.putExtra(Intent.EXTRA_TEXT, "标题：" + moodBeans.get(postion).getTitle()
                            + "\n" + "心情：" + moodBeans.get(postion).getContent()
                            + "\n" + "位置:" + moodBeans.get(postion).getPostion()
                            + "\n" + Utils.getDate());
                    intent.setType("text/plain");
                } else {
                    File f = new File(moodBeans.get(postion).getImagepath());
                    if (f != null && f.exists() && f.isFile()) {
                        intent.setType("image/jpg");
                        Uri u = Uri.fromFile(f);
                        intent.putExtra(Intent.EXTRA_STREAM, u);
                    }
                }
                startActivity(Intent.createChooser(intent, "分享到"));
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    @Override
    public void onRefresh() {
        initData();
    }
}
