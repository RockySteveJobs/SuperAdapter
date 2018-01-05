package com.eagle.adapter.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.eagle.adapter.base.AbstractItem;
import com.eagle.adapter.base.AbstractRecycleAdapter;
import com.eagle.adapter.base.AbstractViewHolder;
import com.eagle.adapter.util.LogUtils;

import java.util.List;

/**
 * Start
 * <p>
 * User:Rocky(email:1247106107@qq.com)
 * Created by Rocky on 2017/11/18 14:14
 * PACKAGE_NAME com.eagle.adapter.common
 * PROJECT_NAME SuperAdapter
 * TODO:
 * Description:
 * <p>
 * Done
 */
public class CommonRecycleAdapter<T extends AbstractItem> extends AbstractRecycleAdapter {

    private RecyclerView mListView;

    public CommonRecycleAdapter(Context context, List<T> listData, RecyclerView listView) {
        super(context, listData);
        this.mListView = listView;


        if (mListView != null) {
            mListView.setOnScrollListener(listener);
        } else {
            LogUtils.w("传入的 RecycleView 为空了");
        }
    }


    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {

        private int lastFirstItem = 0;
        private long timestamp = System.currentTimeMillis();

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
            super.onScrollStateChanged(recyclerView, scrollState);

            if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                LogUtils.d("IDLE - Reload!");
                shouldRequestThumb = true;
                notifyDataSetChanged();
                LogUtils.i("您已经进入了if (scrollState == SCROLL_STATE_IDLE) {");
            } else if (scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                //Scroll
                LogUtils.i("您已经进入了} else if(scrollState == SCROLL_STATE_DRAGGING){");
            } else if (scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                //flying
                shouldRequestThumb = false;
                LogUtils.i("您已经进入了else if(scrollState == SCROLL_STATE_SETTLING)");
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (mListView.getLayoutManager() instanceof LinearLayoutManager) {
                int firstVisibleItem = ((LinearLayoutManager) mListView.getLayoutManager()).findFirstVisibleItemPosition();
                int lastVisibleItem = ((LinearLayoutManager) mListView.getLayoutManager()).findLastVisibleItemPosition();
                int visibleItemCount = lastVisibleItem - firstVisibleItem + 1;

                float dt = System.currentTimeMillis() - timestamp;
                if (firstVisibleItem != lastFirstItem) {
                    double speed = 1 / dt * 100000;
                    lastFirstItem = firstVisibleItem;
                    timestamp = System.currentTimeMillis();
                    LogUtils.d("Speed: " + speed + " elements/second");
                    shouldRequestThumb = speed < visibleItemCount;
                }
            }
        }

    };

    @Override
    protected void bindData(AbstractViewHolder holder, AbstractItem bean, int pos) {
        if (bean != null && holder != null) {
            bean.bindData(holder, shouldRequestThumb);
        }
    }
}
