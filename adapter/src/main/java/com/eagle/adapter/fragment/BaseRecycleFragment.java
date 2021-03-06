package com.eagle.adapter.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eagle.adapter.R;
import com.eagle.adapter.base.AbstractItem;
import com.eagle.adapter.base.BaseEvent;
import com.eagle.adapter.common.CommonRecycleAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecycleFragment extends BaseFragment {

    protected RecyclerView rv_RecycleList;
    protected List<AbstractItem> itemList;
    protected CommonRecycleAdapter recycleAdapter;

    protected SmartRefreshLayout mSRefreshLayout;

    @Override
    public void initView() {
        itemList = new ArrayList<>();

        rv_RecycleList = findViewById(R.id.rv_RecycleList);
        rv_RecycleList.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleAdapter = new CommonRecycleAdapter(getContext(), itemList, rv_RecycleList);
        rv_RecycleList.setAdapter(recycleAdapter);


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
        recycleAdapter.onEvent(event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleAdapter.doClear();
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_base_recycle;
    }

    public void notifyDataSetChanged() {
        recycleAdapter.notifyDataSetChanged();
    }

    public void notifyItemChanged(int pos) {
        recycleAdapter.notifyItemChanged(pos);
    }

    public void removeItem(int pos) {
        if (itemList.size() > pos) {
            itemList.remove(pos);
            recycleAdapter.notifyItemRemoved(pos);
        }
    }

    public void insertItem(int pos, AbstractItem item) {
        if (itemList.size() >= pos) {
            itemList.add(pos, item);
            recycleAdapter.notifyItemInserted(pos);
        }
    }

    public void clearData() {
        itemList.clear();
        recycleAdapter.notifyDataSetChanged();
    }

    public void addListData(List<AbstractItem> list) {
        if (list != null && !list.isEmpty()) {
            itemList.addAll(list);
            recycleAdapter.notifyDataSetChanged();
        }
    }
}
