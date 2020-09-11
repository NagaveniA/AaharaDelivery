package com.aahara.aaharadelivery.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryBean implements Serializable
{



    @SerializedName("order")
    @Expose
    private List<Order> order = null;
    private final static long serialVersionUID = 9161165426489371856L;

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public class Order implements Serializable {

        @SerializedName("sk_order_id")
        @Expose
        private String skOrderId;
        @SerializedName("restuarant_id")
        @Expose
        private String restuarantId;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("total_cost")
        @Expose
        private String totalCost;
        @SerializedName("ordered_date")
        @Expose
        private String orderedDate;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("address_name")
        @Expose
        private String addressName;
        @SerializedName("landmark")
        @Expose
        private String landmark;
         @SerializedName("user_mobile")
         @Expose
         private String userMobile;
        @SerializedName("item")
        @Expose
        private List<Item> item = null;
        private final static long serialVersionUID = 9164711073757581355L;

        public String getSkOrderId() {
            return skOrderId;
        }

        public void setSkOrderId(String skOrderId) {
            this.skOrderId = skOrderId;
        }

        public String getRestuarantId() {
            return restuarantId;
        }

        public void setRestuarantId(String restuarantId) {
            this.restuarantId = restuarantId;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(String totalCost) {
            this.totalCost = totalCost;
        }

        public String getOrderedDate() {
            return orderedDate;
        }

        public void setOrderedDate(String orderedDate) {
            this.orderedDate = orderedDate;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }


        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }

    }
    public class Item implements Serializable
    {

        @SerializedName("sk_order_details")
        @Expose
        private String skOrderDetails;
        @SerializedName("sk_items_id")
        @Expose
        private String skItemsId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("cart_count")
        @Expose
        private String cartCount;
        private final static long serialVersionUID = 3282934178126067635L;

        public String getSkOrderDetails() {
            return skOrderDetails;
        }

        public void setSkOrderDetails(String skOrderDetails) {
            this.skOrderDetails = skOrderDetails;
        }

        public String getSkItemsId() {
            return skItemsId;
        }

        public void setSkItemsId(String skItemsId) {
            this.skItemsId = skItemsId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getCartCount() {
            return cartCount;
        }

        public void setCartCount(String cartCount) {
            this.cartCount = cartCount;
        }

    }

}





