package com.eagle.adapter.item;


import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eagle.adapter.R;
import com.eagle.adapter.base.AbstractViewHolder;
import com.eagle.adapter.base.BaseEvent;

public class TestOneItem extends BaseTestItem<TestOne, TestOneItem.ViewHolder> implements View.OnClickListener {

    public TestOneItem(TestOne item, int itemType) {
        super(item, itemType);
    }

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_test_one;
    }

    @Override
    public void onEvent(BaseEvent event) {
        super.onEvent(event);

        TestOne testOne = getRealData();
        if (testOne != null) {
            //todo just do something & notify change

            notifyDataSetChanged(getPosition());

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtv_Test:
                TestOne testOne = getRealData();
                if (testOne != null) {
                    testOne.title += "*";
                    notifyDataSetChanged(getPosition());
                }
                break;
            default:
                break;
        }

        Toast.makeText(mContext, "you click pos :" + getPosition(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected TestOneItem.ViewHolder getViewHolder(View root) {
        return new ViewHolder(root);
    }

    @Override
    public void bindData(TestOneItem.ViewHolder holder, boolean shouldRequest) {
        TestOne testOne = getRealData();

        holder.txtv_Test.setText(String.valueOf(getPosition()) + testOne.title);
        holder.txtv_Test.setOnClickListener(this);
    }


    static class ViewHolder extends AbstractViewHolder {
        private TextView txtv_Test;

        private ViewHolder(View view) {
            super(view);
            txtv_Test = findViewById(R.id.txtv_Test);
        }
    }
}
