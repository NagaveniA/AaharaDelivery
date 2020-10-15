package com.aahara.aaharadelivery.Model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderHistoryBean implements Serializable {

    @SerializedName("order")
    @Expose
    private ArrayList<Order> order = null;

    public ArrayList<Order> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Order> order) {
        this.order = order;
    }
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        private Object addressName;
        @SerializedName("landmark")
        @Expose
        private Object landmark;
        @SerializedName("user_mobile")
        @Expose
        private String userMobile;
        @SerializedName("restuarant_Latitude")
        @Expose
        private String restuarantLatitude;
        @SerializedName("restuarant_Longitude")
        @Expose
        private String restuarantLongitude;
        @SerializedName("user_latitude")
        @Expose
        private Object userLatitude;
        @SerializedName("user_longitude")
        @Expose
        private Object userLongitude;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("Delivery_Charges")
        @Expose
        private String deliveryCharges;
        @SerializedName("payable_amount")
        @Expose
        private Integer payableAmount;
        @SerializedName("item")
        @Expose
        private List<Item> item = null;

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
            return (String) addressName;
        }

        public void setAddressName(Object addressName) {
            this.addressName = addressName;
        }

        public String getLandmark() {
            return (String) landmark;
        }

        public void setLandmark(Object landmark) {
            this.landmark = landmark;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public String getRestuarantLatitude() {
            return restuarantLatitude;
        }

        public void setRestuarantLatitude(String restuarantLatitude) {
            this.restuarantLatitude = restuarantLatitude;
        }

        public String getRestuarantLongitude() {
            return restuarantLongitude;
        }

        public void setRestuarantLongitude(String restuarantLongitude) {
            this.restuarantLongitude = restuarantLongitude;
        }

        public Object getUserLatitude() {
            return userLatitude;
        }

        public void setUserLatitude(Object userLatitude) {
            this.userLatitude = userLatitude;
        }

        public Object getUserLongitude() {
            return userLongitude;
        }

        public void setUserLongitude(Object userLongitude) {
            this.userLongitude = userLongitude;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getDeliveryCharges() {
            return deliveryCharges;
        }

        public void setDeliveryCharges(String deliveryCharges) {
            this.deliveryCharges = deliveryCharges;
        }

        public String getPayableAmount() {
            return String.valueOf(payableAmount);
        }

        public void setPayableAmount(Integer payableAmount) {
            this.payableAmount = payableAmount;
        }

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }

    }

    public class Item implements Serializable {

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