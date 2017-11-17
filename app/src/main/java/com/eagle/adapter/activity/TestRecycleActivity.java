package com.eagle.adapter.activity;

import com.eagle.adapter.base.BaseEvent;
import com.eagle.adapter.item.BaseTestItem;
import com.eagle.adapter.item.TestOne;
import com.eagle.adapter.item.TestOneItem;


public class TestRecycleActivity extends BaseRecycleActivity {

    @Override
    public void initListData() {
        setToolBarTitle("Super Adapter");
        audoRefreshData();
    }

    @Override
    public void refreshListData() {

        //todo call refresh api

        postDelayed(() -> {
            clearData();

            for (int i = 0; i < 20; i++) {
                itemList.add(new TestOneItem(new TestOne(), BaseTestItem.TEST_COMMON_ITEM_TYPE));
            }

            notifyDataSetChanged();
            finishRefresh();

        }, 1000);
    }

    @Override
    public void loadMoreListData() {

        //todo call load more api

        postDelayed(() -> {

            for (int i = 0; i < 20; i++) {
                itemList.add(new TestOneItem(new TestOne(), BaseTestItem.TEST_COMMON_ITEM_TYPE));
            }

            notifyDataSetChanged();
            finishLoadMore();

        }, 1000);
    }

    @Override
    public void onEvent(BaseEvent event) {
        super.onEvent(event);
    }
}
