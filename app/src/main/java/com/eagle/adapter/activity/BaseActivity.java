package com.eagle.adapter.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eagle.adapter.R;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends AppCompatActivity {

    protected LayoutInflater mInflater;
    private ViewGroup fram_Container;

    protected Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        setContentView(R.layout.activity_base);
        fram_Container = findViewById(R.id.fram_Container);
        if (getContentLayout() > 0) {
            mInflater.inflate(getContentLayout(), fram_Container);
        }
        EventBus.getDefault().register(this);
        initView();
    }

    public void setToolBarTitle(String title) {

    }

    public void setToolBarTitle(int title) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public abstract int getContentLayout();

    public abstract void initView();

    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    public void postDelayed(Runnable runnable, long delay) {
        handler.postDelayed(runnable, delay);
    }

    public void addView(View view) {
        fram_Container.addView(view);
    }

}
