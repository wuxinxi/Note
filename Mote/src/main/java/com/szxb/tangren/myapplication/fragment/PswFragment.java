package com.szxb.tangren.myapplication.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szxb.tangren.myapplication.Adapter.PswAdapter;
import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.bean.PswBean;
import com.szxb.tangren.myapplication.db.Psw_DBManager;
import com.szxb.tangren.myapplication.inteFace.CustomClickListener;
import com.szxb.tangren.myapplication.inteFace.CustomItemLongClickListener;
import com.szxb.tangren.myapplication.utils.Control;
import com.szxb.tangren.myapplication.utils.CustomDiglog;
import com.szxb.tangren.myapplication.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangRen on 2016/7/20.
 */
public class PswFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, CustomClickListener, CustomItemLongClickListener {

    private View rootView;

    private List<PswBean> list_psw = new ArrayList<PswBean>();

    private static SwipeRefreshLayout refreshLayout;

    private RecyclerView recyclerView;

    private static PswAdapter mAdapter;

    private CustomDiglog mDialog;

    private LinearLayout linearLayout;

    private Control control;

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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout);

        mAdapter = new PswAdapter(getActivity(), list_psw, linearLayout);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        mAdapter.setItemLongClickListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);

        control = new Control(getActivity());

    }

    private void initData() {
        if (list_psw.size() > 0)
            list_psw.clear();
        try {
            list_psw.addAll(Psw_DBManager.query());
        } catch (Exception e) {

        }

        handler.sendEmptyMessage(1);
    }


    private void startRefresh() {

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter filter = new IntentFilter();
        filter.addAction("refresh_psw");
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
        Utils.startActivity_psw(list_psw, postion, getActivity());
    }

    @Override
    public void setItemLongClick(View view, final int postion) {
        mDialog = new CustomDiglog(getActivity());
        mDialog.setClickListenter(new CustomDiglog.ClickListenerInterface() {
            @Override
            public void del() {

                Psw_DBManager.delete(list_psw.get(postion).getId());
                list_psw.remove(postion);
                handler.sendEmptyMessage(1);

                mDialog.dismiss();
            }

            @Override
            public void edit() {
                Utils.startActivity_psw(list_psw, postion, getActivity());mDialog.dismiss();
            }

            @Override
            public void copy() {
                control.copy("账户：" + list_psw.get(postion).getName()
                        + "\n" + "账号：" + list_psw.get(postion).getZhanghao()
                        + "\n" + "密码:" + list_psw.get(postion).getPsw()
                        + "\n" + "留言：" + list_psw.get(postion).getOther()
                        + "\n" + Utils.getDate());
                mDialog.dismiss();
            }

            @Override
            public void share() {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "账户：" + list_psw.get(postion).getName()
                        + "\n" + "账号：" + list_psw.get(postion).getZhanghao()
                        + "\n" + "密码:" + list_psw.get(postion).getPsw()
                        + "\n" + "留言：" + list_psw.get(postion).getOther()
                        + "\n" + Utils.getDate());
                intent.setType("text/plain");
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
