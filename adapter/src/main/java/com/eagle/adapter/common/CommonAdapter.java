package com.eagle.adapter.common;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;


import com.eagle.adapter.base.AbstractAdapter;
import com.eagle.adapter.base.AbstractItem;
import com.eagle.adapter.base.AbstractViewHolder;
import com.eagle.adapter.util.LogUtils;

import java.util.List;

/**
 * Start
 * <p/>
 * User:Rocky(email:1247106107@qq.com)
 * Created by Rocky on 2017/11/18  11:12
 * PACKAGE_NAME com.eagle.adapter.common
 * PROJECT_NAME SuperAdapter
 * TODO:
 * Description: 这是一个超级通用的Adapter，只要知道ListView中大概有几种Item类型，都可以用这个Adapter
 * <p/>
 * Done
 */
public class CommonAdapter<T extends AbstractItem, VH extends AbstractViewHolder> extends AbstractAdapter<T, VH> {

    private ListView mListView;
    private GridView mGridView;
    private int itemTypeCount = 0;

    public CommonAdapter(Context context, List<T> listData, int itemTypeCount) {
        super(context, listData);
        this.itemTypeCount = itemTypeCount;
    }

    public CommonAdapter(Context context, List<T> listData, ListView listView, int itemTypeCount) {
        super(context, listData);
        this.mListView = listView;
        this.itemTypeCount = itemTypeCount;
        if (mListView != null) {
            mListView.setOnScrollListener(listener);
        } else {
            LogUtils.w("传入的 ListView 为空了");
        }
    }

    public CommonAdapter(Context context, List<T> listData, GridView gridView, int itemTypeCount) {
        super(context, listData);
        this.mGridView = gridView;
        this.itemTypeCount = itemTypeCount;
        if (mGridView != null) {
            mGridView.setOnScrollListener(listener);
        } else {
            LogUtils.w("传入的 GridView 为空了");
        }
    }

    private AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() {
        private int lastFirstItem = 0;
        private long timestamp = System.currentTimeMillis();

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE) {
                LogUtils.d("IDLE - Reload!");
                shouldRequestThumb = true;
                notifyDataSetChanged();
                LogUtils.i("您已经进入了if (scrollState == SCROLL_STATE_IDLE) {");
            } else if (scrollState == SCROLL_STATE_FLING) {
                shouldRequestThumb = false;
                LogUtils.i("您已经进入了} else if(scrollState == SCROLL_STATE_FLING){");
            } else if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                LogUtils.i("您已经进入了else if(scrollState == SCROLL_STATE_TOUCH_SCROLL)");
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            float dt = System.currentTimeMillis() - timestamp;
            if (firstVisibleItem != lastFirstItem) {
                double speed = 1 / dt * 100000;
                lastFirstItem = firstVisibleItem;
                timestamp = System.currentTimeMillis();
                LogUtils.d("Speed: " + speed + " elements/second");
                shouldRequestThumb = speed < visibleItemCount;
            }
        }
    };

    @Override
    public int getItemViewType(int position) {
        return mListData.get(position).getItemViewType();
    }

    @Override
    protected int getItemTypeCount() {
        return itemTypeCount;
    }

    @Override
    protected void bindData(AbstractViewHolder holder, AbstractItem bean, int pos) {
        if (bean != null && holder != null) {
            bean.bindData(holder, shouldRequestThumb);
        }
    }
}
