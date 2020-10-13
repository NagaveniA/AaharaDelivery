package com.aahara.aaharadelivery.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.aahara.aaharadelivery.History;
import com.aahara.aaharadelivery.HomeActivity;
import com.aahara.aaharadelivery.Interface.AlertDailog;
import com.aahara.aaharadelivery.Model.DeliveryBean;
import com.aahara.aaharadelivery.Model.OrderItemListModel;
import com.aahara.aaharadelivery.R;
import com.aahara.aaharadelivery.google_map.MapsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private Context context,mcontext;
    private static final int MY_PERMISSIONS = 1;
    ArrayList<OrderItemListModel> model = new ArrayList<>();

    public HistoryAdapter(ArrayList<OrderItemListModel> model,Context mcontext) {
        this.model = model;
        this.mcontext = mcontext;
    }

    private List<DeliveryBean.Order> deliveryBeanList = new ArrayList<>(  );
    private List<DeliveryBean.Item> delivaryBeamListItem = new ArrayList<>();
    AlertDailog mcallBack;
    ArrayAdapter<String>  dataAdapter;
    HomeActivity home;
    public HistoryAdapter(Context context, List<DeliveryBean.Order> deliveryBeanList, AlertDailog mcallBack, HomeActivity home) {
        this.context = context;
        this.deliveryBeanList = deliveryBeanList;
        this.mcallBack = mcallBack;
        this.home = home;
    }


  @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.history_card, parent, false );
        return new MyViewHolder( view );
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DeliveryBean.Order deliveryBean = deliveryBeanList.get( position );
        holder.tvShopName.setText( deliveryBean.getRestaurantName() );
        holder.landmark.setText( deliveryBean.getLandmark() );
        holder.tvLocation.setText( deliveryBean.getUserMobile() );
        holder.tvLongitude.setText( deliveryBean.getLongitude());
        holder.tvLatitude.setText( deliveryBean.getLatitude());
        holder.viewAdress.setText(deliveryBean.getAddressName());
        holder.tvOrderedOn.setText( deliveryBean.getOrderedDate() );
        holder.tvTotalAmount.setText( deliveryBean.getTotalCost() );

        ////
        holder.address_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // boolean click_event = false;
                if(holder.ll_address.getVisibility()==View.GONE){
                    holder.ll_address.setVisibility(View.VISIBLE);
                    holder.dropImg1.setVisibility(View.GONE);

                    holder.dropImg.setImageResource(R.drawable.ic_arrow_drop_up_24);

                }
                else if(holder.ll_address.getVisibility()==View.VISIBLE)
                {
                    holder.ll_address.setVisibility(View.GONE);
                    holder.dropImg.setImageResource(R.drawable.ic_arrow_drop_down_24);

                }
            }
        });
        ///
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mcontext,LinearLayoutManager.HORIZONTAL,false);
        holder.items_recycler.setLayoutManager(layoutManager);
        OrderItemListAdapter orderItemListAdapter = new OrderItemListAdapter(deliveryBean.getItem(),mcontext);

        //deliveryBean.getItem();
        holder.items_recycler.setAdapter(orderItemListAdapter);
        holder.tvOrderstatus.setText(deliveryBean.getOrder_status());

        List<String> orderList = new ArrayList<String>(  );
        orderList.add( "--Select--" );
        orderList.add( "picked_up" );
        orderList.add( "delivered" );
        holder.order_id.setText(deliveryBean.getSkOrderId());


        dataAdapter = new ArrayAdapter<String>(context,R.layout.layout_spinner,orderList);
        dataAdapter.setDropDownViewResource(R.layout.layout_spinner );
        holder.orderSpinnar.setAdapter( dataAdapter );
        String orderstatus = holder.tvOrderstatus.getText().toString();
        if(orderstatus.equals("placed"))
        {
            holder.orderSpinnar.setSelection(0,false);
        }else if (orderstatus.equals("OutForDelivery")){
            holder.orderSpinnar.setSelection(1,false);
        }


        holder.orderSpinnar.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String order_no = holder.order_id.getText().toString();

                 if(item == "delivered"){
                    home.callApi1( position,item ,order_no);
                    home.callApi();
                    }
                    else
                    {
                        String item1 = "OutForDelivery";
                        home.callApi1( position,item1 ,order_no);
                    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        holder.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + holder.tvLocation.getText().toString()));
                         v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return deliveryBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * ButterKnife Code
         **/
        @BindView( R.id.itemsRecycler )
        RecyclerView items_recycler;
        @BindView( R.id.order_spenner )
        Spinner orderSpinnar;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_location)
        TextView tvLocation;
//        @BindView(R.id.tv_items)
//        TextView tvItems;
        @BindView(R.id.tv_latitude)
        TextView tvLatitude;
        @BindView(R.id.tv_longitude)
        TextView tvLongitude;
        @BindView(R.id.tv_order_status)
        TextView tvOrderstatus;
        @BindView(R.id.tv_ordered_on)
        TextView tvOrderedOn;
        @BindView(R.id.tv_total_amount)
        TextView tvTotalAmount;
        @BindView(R.id.tv_closed)
        TextView tvClosed;
        @BindView(R.id.tv_cancel_order)
        TextView tvCancelOrder;
//        @BindView(R.id.address)
//        TextView tvAddress;
        @BindView(R.id.address)
        Button address;
        @BindView(R.id.tv_orderId)
        TextView order_id;
        @BindView(R.id.landmark)
        TextView landmark;
        @BindView(R.id.ll_address)
        LinearLayout ll_address;
        @BindView(R.id.view_address)
        TextView viewAdress;
        @BindView(R.id.address_dropdown)
        TextView address_dropdown;
        @BindView(R.id.drop_img)
        ImageView dropImg;
        @BindView(R.id.drop_img1)
        ImageView dropImg1;




        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            items_recycler  = itemView.findViewById(R.id.itemsRecycler);
            address.setOnClickListener( this );

        }


        @OnClick({R.id.address})
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String shopName = tvShopName.getText().toString();
            String latitude = tvLatitude.getText().toString();
            String longitude = tvLongitude.getText().toString();
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);

           String uri = "http://maps.google.com/maps?q=loc:" + lat + "," + lon + " (" +"SHASHI"+ ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(uri)));
            context.startActivity(intent);
        }
    }

}


