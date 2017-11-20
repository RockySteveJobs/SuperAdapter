package com.eagle.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.eagle.adapter.util.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Start
 * <p/>
 * User:Rocky(email:1247106107@qq.com)
 * Created by Rocky on 2017/11/18  11:07
 * PACKAGE_NAME com.eagle.adapter.base
 * PROJECT_NAME SuperAdapter
 * TODO:
 * Description:
 * <p/>
 * Done
 */
public abstract class AbstractRecycleAdapter<T extends AbstractItem, VH extends AbstractViewHolder> extends RecyclerView.Adapter<AbstractViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mListData;
    //是否需要添加到根View
    protected boolean attachToRoot;
    //这个是对图片的请求很有用处,即true时请求,false不请求(比如用户快速滑动页面)
    protected boolean shouldRequestThumb = true;
    //接收事件的Item列表
    private HashSet<T> eventList;

    /**
     * @param listData 需要绑定的List对象
     */
    public void setListData(List<T> listData) {
        if (listData != null) {
            mListData.clear();
            mListData.addAll(listData);
            notifyDataSetChanged();
        }
    }

    public List<T> getListData() {
        return mListData;
    }

    /**
     * @param listData 需要加载更多的对象
     */
    public void addListData(List<T> listData) {
        if (listData != null) {
            mListData.addAll(listData);
            notifyDataSetChanged();
        }
    }

    /**
     * 清除所有的对象
     */
    public void clearListData() {
        if (mListData != null) {
            mListData.clear();
            notifyDataSetChanged();
        }
    }


    public void addItem(int position, T item) {
        mListData.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mListData.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * 构造方法,无特殊需求,子类中可以不做人任何操作
     *
     * @param context  当前上下文对象
     * @param listData 需要绑定的List对象
     */
    public AbstractRecycleAdapter(Context context, List<T> listData) {
        init(context, listData);
    }

    /**
     * 构造方法,无特殊需求,子类中可以不做人任何操作
     *
     * @param context  当前上下文对象
     * @param listData 需要绑定的List对象
     * @param params   有可能传递多个参数
     */
    public AbstractRecycleAdapter(Context context, List<T> listData, Object... params) {
        init(context, listData);
    }

    /**
     * @param context  当前上下文对象
     * @param listData 需要绑定的List对象
     */
    private void init(Context context, List<T> listData) {
        mContext = context;
        if (listData == null) {
            this.mListData = new ArrayList<T>();
        } else {
            this.mListData = listData;
        }
        mInflater = LayoutInflater.from(mContext);
        eventList = new HashSet<>();
    }


    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mListData.get(position).getItemViewType();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        T t = getItemByViewType(viewType);
        if (t == null) {
            LogUtils.e("AbstractRecycleAdapter=====onCreateViewHolder====if (t == null) {====没有创建ViewHolder");
            return null;
        }
        if (t.getItemViewLayout() <= 0) {
            LogUtils.e("AbstractRecycleAdapter=====onCreateViewHolder====if (t.getItemViewLayout() <= 0) {====没有创建ViewHolder");
            return null;
        }
        View view = mInflater.inflate(t.getItemViewLayout(), parent, attachToRoot);
        if (view == null) {
            LogUtils.e("AbstractRecycleAdapter=====onCreateViewHolder====if (view == null) {====没有创建ViewHolder");
            return null;
        }
        AbstractViewHolder holder = t.getViewHolder(view);
        if (holder == null) {
            LogUtils.e("AbstractRecycleAdapter=====onCreateViewHolder====if (holder == null) {====没有创建ViewHolder");
            return null;
        }
        LogUtils.i("AbstractRecycleAdapter===========onCreateViewHolder========创建了ViewHolder");
        return (VH) holder;
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        if (holder == null) {
            LogUtils.e("AbstractRecycleAdapter===========onBindViewHolder========if (holder == null) {====");
            return;
        }

        final int currentPosition = holder.getAdapterPosition();
        if (currentPosition == RecyclerView.NO_POSITION) {
            LogUtils.e("AbstractRecycleAdapter===========onBindViewHolder========if (currentPosition == RecyclerView.NO_POSITION) {====");
            return;
        }
        //得到java对象
        T data = getItem(position);

        if (data == null) {
            LogUtils.e("AbstractRecycleAdapter===========onBindViewHolder========if (data == null) {====");
            return;
        }

        //设置位置
        data.setPosition(position);

        if (data.getContext() != mContext) {
            data.setContext(mContext);
        }

        //设置相应的Adapter 和 Loader ,更方便的处理数据
        if (data.getRecycleAdapter() != this) {
            data.setRecycleAdapter(this);
        }
        if (data.isReceiveEvent() && !eventList.contains(data)) {
            eventList.add(data);
        }
        if (data.getViewHolder() != holder) {
            data.setViewHolder(holder);
        }
        bindData((VH) holder, data, position);
        LogUtils.i("AbstractRecycleAdapter===========onBindViewHolder ========调用了bindata方法 " + data.getItemViewType());
    }


    private T getItem(int i) {
        return mListData.get(i);
    }


    private T getItemByViewType(int viewType) {
        for (int i = 0; i < mListData.size(); i++) {
            T t = mListData.get(i);
            if (t != null && viewType == t.getItemViewType()) {
                return t;
            }
        }
        return null;
    }


    /**
     * 这个方法主要是把List<T>中的数据绑定到界面中
     *
     * @param holder ViewHolder对象,在子类中使用时,转换成自己定义的ViewHolder
     * @param bean   JavaBean,这个是根据业务对象Bean而定
     * @param pos    绑定数据时,所在的位置,即哪一个Item
     */
    protected abstract void bindData(VH holder, T bean, int pos);


    /**
     * 把Adapter接收的事件传递给需要接收事件的Item
     *
     * @param event 事件
     */
    public void onEvent(BaseEvent event) {
        Iterator<T> iterator = eventList.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            item.onEvent(event);
        }
    }


    /**
     * @return 得到当前的上下文对象
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 请务必做一些清理操作,
     * 并在Activity或者Fragment的onDestroy中调用
     */
    public void doClear() {
        if (mListData != null) {
            LogUtils.w("AbstractRecycleAdapter doClear if (mListData != null) { mListData.size()" + mListData.size());
            mListData.clear();
            mListData = null;
        }
        if (eventList != null) {
            LogUtils.w("AbstractRecycleAdapter doClear eventList.size(): " + eventList.size());
            eventList.clear();
            eventList = null;
        }

        mInflater = null;
        mContext = null;
        LogUtils.w("AbstractRecycleAdapter public void doClear() {");
    }

}
