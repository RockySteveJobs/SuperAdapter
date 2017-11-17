package com.eagle.adapter.item;


import com.eagle.adapter.base.AbstractItem;
import com.eagle.adapter.base.AbstractViewHolder;

public abstract class BaseTestItem<D, VH extends AbstractViewHolder> extends AbstractItem<D, VH> {

    public static int itemTypeCount = 0;

    public static final int TEST_COMMON_ITEM_TYPE = itemTypeCount++;
    public static final int TEST_DESCITEM_TYPE = itemTypeCount++;

    public BaseTestItem(D data, int itemType) {
        super(data, itemType);
    }
}
