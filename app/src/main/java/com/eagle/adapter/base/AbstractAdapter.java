package com.eagle.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.eagle.adapter.util.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


/**
 * Start
 * <p/>
 * User:Rocky(email:1247106107@qq.com)
 * Created by Rocky on 2017/11/18  13:51
 * PACKAGE_NAME com.eagle.adapter.base
 * PROJECT_NAME SuperAdapter
 * TODO:
 * Description: 继承BaseAdapter时往往需要写很多重复的代码,为了使 使用更方便, 以后使用时,可以直接继承此类
 * <p/>
 * Done
 */
public abstract class AbstractAdapter<T extends AbstractItem, VH extends AbstractViewHolder> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mListData;
    //是否需要根View进行In
    protected boolean needRoot;
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

    public List<T> getListData() {
        return mListData;
    }

    /**
     * 构造方法,无特殊需求,子类中可以不做人任何操作
     *
     * @param context  当前上下文对象
     * @param listData 需要绑定的List对象
     */
    public AbstractAdapter(Context context, List<T> listData) {
        init(context, listData);
    }

    /**
     * 构造方法,无特殊需求,子类中可以不做人任何操作
     *
     * @param context  当前上下文对象
     * @param listData 需要绑定的List对象
     * @param params   有可能传递多个参数
     */
    public AbstractAdapter(Context context, List<T> listData, Object... params) {
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
    public int getViewTypeCount() {
        LogUtils.i("protected static int T.getViewTypeCount() { " + getItemTypeCount());
        return getItemTypeCount();
    }

    protected abstract int getItemTypeCount();

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public T getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //得到java对象
        T data = getItem(position);
        if (data == null || data.getItemViewLayout() <= 0) {
            LogUtils.e("AbstractAdapter===========getView ========if (data == null || data.getItemViewLayout() <= 0) {====");
            return null;
        }

        //得到ViewHolder
        AbstractViewHolder viewHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof AbstractViewHolder)) {
            convertView = mInflater.inflate(data.getItemViewLayout(), parent, needRoot);

            viewHolder = data.getViewHolder(convertView);
            convertView.setTag(viewHolder);
            LogUtils.i("AbstractAdapter===========getView ========控件没有重用");
        } else {
            LogUtils.i("AbstractAdapter===========getView ========控件重用了");
            viewHolder = (AbstractViewHolder) convertView.getTag();
        }

        //设置位置
        data.setPosition(position);

        //设置相应的Adapter 和 Loader ,更方便的处理数据
        if (data.getContext() != mContext) {
            data.setContext(mContext);
        }
        if (data.getAdapter() != this) {
            data.setAdapter(this);
        }
        if (data.getViewHolder() != viewHolder) {
            data.setViewHolder(viewHolder);
        }
        if (data.isReceiveEvent() && !eventList.contains(data)) {
            eventList.add(data);
        }
        LogUtils.i("AbstractAdapter===========getView ========调用了bindData方法 " + data.getItemViewType());
        bindData((VH) viewHolder, data, position);
        return convertView;
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
            LogUtils.w("AbstractAdapter doClear if (mListData != null) { mListData.size()" + mListData.size());
            mListData.clear();
            mListData = null;
        }
        if (eventList != null) {
            LogUtils.w("AbstractAdapter doClear eventList.size(): " + eventList.size());
            eventList.clear();
            eventList = null;
        }
        mInflater = null;
        mContext = null;
        LogUtils.w("AbstractAdapter public void doClear() {");
    }
}
