package com.eagle.adapter.activity;

import android.widget.TextView;

import com.eagle.adapter.R;

public class MainActivity extends BaseActivity {

    private TextView txtv_CommonTest;
    private TextView txtv_ListTest;
    private TextView txtv_RecycleTest;

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setToolBarTitle("Main");
        txtv_CommonTest = findViewById(R.id.txtv_CommonTest);
        txtv_CommonTest.setOnClickListener(view -> {
            TestCommonActivity.startActivity(this);
        });
        txtv_ListTest = findViewById(R.id.txtv_ListTest);
        txtv_ListTest.setOnClickListener(view -> {
            TestListActivity.startActivity(this);
        });
        txtv_RecycleTest = findViewById(R.id.txtv_RecycleTest);
        txtv_RecycleTest.setOnClickListener(view -> {
            TestRecycleActivity.startActivity(this);
        });
    }
}
