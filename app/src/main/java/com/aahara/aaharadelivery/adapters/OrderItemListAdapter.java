package com.aahara.aaharadelivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aahara.aaharadelivery.Model.DeliveryBean;
import com.aahara.aaharadelivery.Model.OrderItemListModel;
import com.aahara.aaharadelivery.R;

import java.util.ArrayList;
import java.util.List;

public class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.MyViewHolder> {
    List<DeliveryBean.Item> delivaryBeamListItem = new ArrayList<>();
    Context context;



    public OrderItemListAdapter(List<DeliveryBean.Item> delivaryBeanItemList,Context mcontext)
    {
        this.delivaryBeamListItem = delivaryBeanItemList;
        this.context = mcontext;
    }

    @NonNull
    @Override
    public OrderItemListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v =  layoutInflater.inflate(R.layout.layout_order_item_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemListAdapter.MyViewHolder holder, int position) {
        holder.cartcount.setText(delivaryBeamListItem.get(position).getCartCount());
        holder.item_name.setText(delivaryBeamListItem.get(position).getItemName());
        if(position==getItemCount()-1)
        {
            holder.comma.setVisibility(View.GONE);
        }
        else
        {
            holder.comma.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return delivaryBeamListItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name;
        TextView cartcount;
        TextView comma;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            cartcount = itemView.findViewById(R.id.cart_count);
            comma = itemView.findViewById(R.id.comma);
        }
    }
}
