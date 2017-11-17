package com.eagle.adapter.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.eagle.adapter.R;
import com.eagle.adapter.base.AbstractItem;
import com.eagle.adapter.base.BaseEvent;
import com.eagle.adapter.common.CommonAdapter;
import com.eagle.adapter.common.CommonRecycleAdapter;
import com.eagle.adapter.item.BaseTestItem;
import com.eagle.adapter.item.TestOne;
import com.eagle.adapter.item.TestOneItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView listView;
    private RecyclerView recyclerView;
    private List<AbstractItem> itemList;

    private CommonAdapter commonAdapter;
    private CommonRecycleAdapter recycleAdapter;


    @Override
    public void initView() {
        itemList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            itemList.add(new TestOneItem(new TestOne(), BaseTestItem.TEST_COMMON_ITEM_TYPE));
        }

        listView = findViewById(R.id.lv_Test);
        commonAdapter = new CommonAdapter(this, itemList, listView, BaseTestItem.itemTypeCount);
        listView.setAdapter(commonAdapter);

        recyclerView = findViewById(R.id.rv_Test);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new CommonRecycleAdapter(this, itemList, recyclerView);
        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    public void onEvent(BaseEvent event) {
        commonAdapter.onEvent(event);
        recycleAdapter.onEvent(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        commonAdapter.doClear();
        recycleAdapter.doClear();
    }
}
