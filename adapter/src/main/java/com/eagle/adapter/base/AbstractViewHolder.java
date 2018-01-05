package com.eagle.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eagle.adapter.R;


/**
 * Start
 * <p>
 * User:Rocky(email:1247106107@qq.com)
 * Created by Rocky on 2017/11/18  13:25
 * PACKAGE_NAME com.eagle.adapter.base
 * PROJECT_NAME SuperAdapter
 * TODO:
 * Description:
 * <p>
 * 在子类定义一个自己的ViewHolder并继承此ViewHolder,
 * 并在bindData方法把强转成自己定义的ViewHolder
 * <p/>
 * 在自己定义的ViewHolder中可以定义各种View,
 * 然后在构造方法中通过view.findViewById(id)方式初始化View,即可使用！
 * <p>
 * <p>
 * Done
 */
public abstract class AbstractViewHolder extends RecyclerView.ViewHolder {

    protected View root;

    public AbstractViewHolder(View view) {
        super(view);
        root = view;
    }

    protected final <T extends View> T findViewById(int id) {
        return root.findViewById(id);
    }


}
