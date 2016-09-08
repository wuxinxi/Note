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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szxb.tangren.myapplication.Adapter.NoteAdapter;
import com.szxb.tangren.myapplication.R;
import com.szxb.tangren.myapplication.bean.NoteBean;
import com.szxb.tangren.myapplication.db.DBManager;
import com.szxb.tangren.myapplication.inteFace.CustomClickListener;
import com.szxb.tangren.myapplication.inteFace.CustomItemLongClickListener;
import com.szxb.tangren.myapplication.utils.Control;
import com.szxb.tangren.myapplication.utils.CustomDiglog;
import com.szxb.tangren.myapplication.utils.Toast;
import com.szxb.tangren.myapplication.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangRen on 2016/7/20.
 */
public class MynoteFragment extends Fragment implements CustomClickListener, CustomItemLongClickListener, SwipeRefreshLayout.OnRefreshListener {


    private static SwipeRefreshLayout reFreshLayout;

    private RecyclerView recyclerView;

    private View rootView;

    private List<NoteBean> list_beans = new ArrayList<NoteBean>();

    private static NoteAdapter mAdapter;

    private LinearLayout linearLayout;

    private CustomDiglog mDialog;

    private Control control;


    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 1:
                    mAdapter.notifyDataSetChanged();
                    reFreshLayout.setRefreshing(false);
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.main_main, container, false);
            init();
            initData();
        }

        startRefresh();
        ViewGroup group = (ViewGroup) rootView.getParent();
        if (group != null) {
            group.removeView(rootView);
        }
        return rootView;

    }

    private void init() {

        reFreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.reFreshLayout);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        reFreshLayout.setOnRefreshListener(this);
        reFreshLayout.setColorSchemeResources(R.color.colorAccent);
        mAdapter = new NoteAdapter(getActivity(), list_beans, linearLayout);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        mAdapter.setItemLongClickListener(this);

        control = new Control(getActivity());


        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
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

                if (list_beans.get(postion).getN_label().equals("程序猿"))

                    mAdapter.onSwipe(viewHolder);

                else {

                    long id = list_beans.get(postion).getId();

                    DBManager.delete(id);
                    mAdapter.onSwipe(viewHolder);
                }

            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    //当用户添加了日记或者修改了日记用来自动刷新
    private void startRefresh() {

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter filter = new IntentFilter();
        filter.addAction("refresh");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                reFreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        reFreshLayout.setRefreshing(true);
                        initData();
                    }
                });
            }
        };

        manager.registerReceiver(receiver, filter);

    }


    //小提示
    private void initData() {

        String title[] = {"前言", "隐藏功能", "开始使用"};

        String content[] = {"之前版本的Android 4.0 风格太low,这次整体做了改版，让你体验清爽的Material Design风格！"
                , "顶部菜单栏可以导出笔记，也可以做私密设置、背景颜色自定义、软件分享好友，也可以给唐人提建议哦！（将在下一版推出，请期待……）"
                , "点击右下角圆形按钮可以进行添加日记，可以选择日记类型，长按日记可以编辑、删除、复制文本、分享、选择日记向右滑动删除(心情日记是像左滑动删除)"};

        String time[] = {"2016/07/07 19:29:20", "2016/07/07 19:31:18", "2016/07/07 19:33:08"};


        if (title.length > 0)
            list_beans.clear();
        for (int i = 0; i < title.length; i++) {
            NoteBean bean = new NoteBean();
            bean.setN_content(content[i]);
            bean.setN_title(title[i]);
            bean.setN_label("程序猿");
            bean.setN_date(time[i]);
            list_beans.add(bean);
        }
        myNoteData();

        handler.sendEmptyMessage(1);

    }


    private void myNoteData() {
        DBManager manager = new DBManager();
        try {
            list_beans.addAll(0, manager.query());
        } catch (Exception e) {

        }

    }

    @Override
    public void setClickListtener(View view, int postion) {

        Utils.startActivity(list_beans, postion, getActivity());

    }

    @Override
    public void setItemLongClick(View view, final int postion) {
        mDialog = new CustomDiglog(getActivity());
        mDialog.setClickListenter(new CustomDiglog.ClickListenerInterface() {
            @Override
            public void del() {
                if (list_beans.get(postion).getN_label().equals("程序猿")) ;
                else {
                    DBManager.delete(list_beans.get(postion).getId());
                    list_beans.remove(postion);
                    handler.sendEmptyMessage(1);
                }
                mDialog.dismiss();
            }

            @Override
            public void edit() {
                Utils.startActivity(list_beans, postion, getActivity());
                mDialog.dismiss();
            }

            @Override
            public void copy() {
                control.copy(list_beans.get(postion).getN_title()
                        + "\n" + list_beans.get(postion).getN_content()
                        + "\n" + Utils.getDate());
                mDialog.dismiss();
            }

            @Override
            public void share() {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, list_beans.get(postion).getN_title()
                        + "\n" + list_beans.get(postion).getN_content()
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
