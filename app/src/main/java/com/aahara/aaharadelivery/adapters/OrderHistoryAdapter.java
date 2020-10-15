package com.aahara.aaharadelivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aahara.aaharadelivery.Model.OrderHistoryBean;
import com.aahara.aaharadelivery.R;
import com.aahara.aaharadelivery.fragments.HistoryFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {
    ArrayList<OrderHistoryBean.Order> oh_model =new ArrayList<>();
    Context context;
    HistoryFragment historyFragment;
    public OrderHistoryAdapter(){}


    public OrderHistoryAdapter(Context context, ArrayList<OrderHistoryBean.Order> orderHistoryBean, HistoryFragment historyFragment) {

        this.context = context;
        this.oh_model = orderHistoryBean;
        this.historyFragment = historyFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_order_history, parent, false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderHistoryBean.Order orderHistoryBean = oh_model.get(position);
        holder.tvShopName.setText(orderHistoryBean.getRestaurantName());

        holder.tvOrderNo.setText(orderHistoryBean.getSkOrderId());
        holder.tvOrderedOn.setText(orderHistoryBean.getOrderedDate());
        holder.tvItemCost.setText("₹ "+orderHistoryBean.getTotalCost());
        holder.tvDeliveryCost.setText("₹ "+orderHistoryBean.getDeliveryCharges());
        holder.tvDeliveryAmt.setText("₹ "+orderHistoryBean.getPayableAmount());
       holder.tvMobile.setText(orderHistoryBean.getUserMobile());
       holder.tvTotalAmount.setText(orderHistoryBean.getPayableAmount());
       holder.tvorderStatus.setText(orderHistoryBean.getOrderStatus());
       if (orderHistoryBean.getAddressName()!=null)
       {
        holder.tvViewAddress.setText( orderHistoryBean.getAddressName());
        holder.tvLandmark.setText( orderHistoryBean.getLandmark());
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        holder.itemsOrderHistoryRecycler.setLayoutManager(layoutManager);
        OrderHistoryItemListAdapter orderItemListAdapter = new OrderHistoryItemListAdapter(orderHistoryBean.getItem(),context);

        //deliveryBean.getItem();
        holder.itemsOrderHistoryRecycler.setAdapter(orderItemListAdapter);


        ////
        holder.viewdetailsDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // boolean click_event = false;
                if(holder.llDetails.getVisibility()== View.GONE){
                    holder.llDetails.setVisibility(View.VISIBLE);
                    holder.dropImg1.setVisibility(View.GONE);

                    holder.dropImg.setImageResource(R.drawable.ic_arrow_drop_up_24);

                }
                else if(holder.llDetails.getVisibility()== View.VISIBLE)
                {
                    holder.llDetails.setVisibility(View.GONE);
                    holder.dropImg.setImageResource(R.drawable.ic_arrow_drop_down_24);

                }
            }
        });
        ///
    }

    @Override
    public int getItemCount() {
        return oh_model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        /** ButterKnife Code **/
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.tv_restaurant_name)
        TextView tvShopName;
        @BindView(R.id.tv_ordered_on)
        TextView tvOrderedOn;
        @BindView(R.id.tv_orderNo)
        TextView tvOrderNo;
        @BindView(R.id.itemsOrderHistoryRecycler)
        androidx.recyclerview.widget.RecyclerView itemsOrderHistoryRecycler;
        @BindView(R.id.tv_total_amount)
        TextView tvTotalAmount;
        @BindView(R.id.viewdetails_dropdown)
        TextView viewdetailsDropdown;
        @BindView(R.id.drop_img1)
        ImageView dropImg1;
        @BindView(R.id.drop_img)
        ImageView dropImg;
        @BindView(R.id.ll_details)
        LinearLayout llDetails;
        @BindView(R.id.tv_item_cost)
        TextView tvItemCost;
        @BindView(R.id.tv_delivery_cost)
        TextView tvDeliveryCost;
        @BindView(R.id.tv_delivery_amt)
        TextView tvDeliveryAmt;
        @BindView(R.id.tv_view_address)
        TextView tvViewAddress;
        @BindView(R.id.tv_landmark)
        TextView tvLandmark;
        @BindView(R.id.tv_mobile)
        TextView tvMobile;
        @BindView(R.id.tv_orderId)
        TextView tvOrderId;
        @BindView(R.id.tv_closed)
        TextView tvClosed;
        @BindView(R.id.tv_orderstatus)
        TextView tvorderStatus;
        /** ButterKnife Code **/



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
