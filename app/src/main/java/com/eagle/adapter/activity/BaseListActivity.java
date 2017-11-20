package com.eagle.adapter.activity;


import android.widget.ListView;

import com.eagle.adapter.R;
import com.eagle.adapter.base.AbstractItem;
import com.eagle.adapter.base.BaseEvent;
import com.eagle.adapter.common.CommonAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListActivity extends BaseActivity {

    protected ListView lv_CommonList;
    protected List<AbstractItem> itemList;
    protected CommonAdapter adapter;

    protected SmartRefreshLayout mSRefreshLayout;

    @Override
    public void initView() {
        itemList = new ArrayList<>();

        lv_CommonList = findViewById(R.id.lv_CommonList);
        adapter = new CommonAdapter(this, itemList, lv_CommonList, getItemTypeCount());
        lv_CommonList.setAdapter(adapter);


        mSRefreshLayout = findViewById(R.id.srl_RefreshLayout);
        mSRefreshLayout.setEnableRefresh(true);
        mSRefreshLayout.setEnableLoadmore(true);
        mSRefreshLayout.setEnableAutoLoadmore(true);
        mSRefreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshListData();
        });

        mSRefreshLayout.setOnLoadmoreListener(refreshlayout -> {
            loadMoreListData();
        });
        initListData();
    }

    public abstract int getItemTypeCount();

    public abstract void initListData();

    public abstract void refreshListData();

    public abstract void loadMoreListData();

    public void showError() {

    }

    public void showNoData() {

    }

    public void audoRefreshData() {
        mSRefreshLayout.autoRefresh();
    }

    public void finishRefresh() {
        mSRefreshLayout.finishRefresh();
    }

    public void finishLoadMore() {
        mSRefreshLayout.finishLoadmore();
    }

    @Override
    public void onEvent(BaseEvent event) {
        super.onEvent(event);
        adapter.onEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.doClear();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_base_list;
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    public void clearData() {
        itemList.clear();
        adapter.notifyDataSetChanged();
    }

    public void addListData(List<AbstractItem> list) {
        if (list != null && !list.isEmpty()) {
            itemList.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

}