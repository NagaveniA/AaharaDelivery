package com.aahara.aaharadelivery.Model;

public class OrderItemListModel
{
    String item_name;

    public OrderItemListModel(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
