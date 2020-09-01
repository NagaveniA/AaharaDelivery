package com.example.aaharadelivery.Model;

public class OrderBean {

    String order_id,order_type;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public OrderBean(String order_id, String order_type){
    this.order_id = order_id;
    this.order_type = order_type;
}

}
