package com.eagle.adapter.base;

import android.content.Context;
import android.view.View;

/**
 * Start
 * <p/>
 * User:Rocky(email:1247106107@qq.com)
 * Created by Rocky on 2017/11/18  13:51
 * PACKAGE_NAME com.eagle.adapter.base
 * PROJECT_NAME SuperAdapter
 * TODO:
 * Description: ListView 的Item类型基类
 * <p/>
 * Done
 */
public abstract class AbstractItem<D, VH extends AbstractViewHolder> {
    /**
     * item type
     */
    protected int itemType = 0;
    /**
     * 上下文对象
     */
    protected Context mContext;
    /**
     * Item所承载的真正的数据
     */
    private D realData;
    /**
     * the id of the item
     */
    private int itemId;
    /**
     * the position of the item
     */
    private int position;

    private AbstractAdapter adapter;
    private AbstractRecycleAdapter recycleAdapter;
    private AbstractViewHolder viewHolder;
    private boolean isReceiveEvent = false;

    public AbstractRecycleAdapter getRecycleAdapter() {
        return recycleAdapter;
    }

    public void setRecycleAdapter(AbstractRecycleAdapter recycleAdapter) {
        this.recycleAdapter = recycleAdapter;
    }

    public AbstractAdapter getAdapter() {
        return adapter;
    }

    public AbstractItem setAdapter(AbstractAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public AbstractViewHolder getViewHolder() {
        return viewHolder;
    }

    public AbstractItem setViewHolder(AbstractViewHolder viewHolder) {
        this.viewHolder = viewHolder;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public AbstractItem setContext(Context context) {
        this.mContext = context;
        return this;
    }


    public boolean isReceiveEvent() {
        return isReceiveEvent;
    }

    public AbstractItem setReceiveEvent(boolean receiveEvent) {
        isReceiveEvent = receiveEvent;
        return this;
    }

    /**
     * 用户不同的Item信息交互
     *
     * @param event
     */
    public void onEvent(BaseEvent event) {

    }

    private AbstractItem() {

    }

    public AbstractItem(D data, int itemType) {
        this.realData = data;
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public AbstractItem setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public AbstractItem setPosition(int position) {
        this.position = position;
        return this;
    }

    public D getRealData() {
        return realData;
    }

    public AbstractItem setRealData(D realData) {
        this.realData = realData;
        return this;
    }


    /**
     * 这个是直接获取对应Item的布局文件如：R.layout.xxxx.xml
     *
     * @return resId 布局文件资源ID
     */
    protected abstract int getItemViewLayout();

    protected abstract VH getViewHolder(View root);


    public int getItemViewType() {
        return itemType;
    }

    /**
     * 请勿随意调用
     *
     * @param holder
     * @param shouldRequest
     */
    public abstract void bindData(VH holder, boolean shouldRequest);

    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else if (recycleAdapter != null) {
            recycleAdapter.notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged(int pos) {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else if (recycleAdapter != null) {
            recycleAdapter.notifyItemChanged(pos);
        }
    }

    protected void setVisibility(View view, int visibility) {
        if (view == null || view.getVisibility() == visibility) {
            return;
        }
        view.setVisibility(visibility);
    }

}
