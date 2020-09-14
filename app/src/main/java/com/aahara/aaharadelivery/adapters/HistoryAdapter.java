package com.aahara.aaharadelivery.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.aahara.aaharadelivery.HomeActivity;
import com.aahara.aaharadelivery.Interface.AlertDailog;
import com.aahara.aaharadelivery.Model.DeliveryBean;
import com.aahara.aaharadelivery.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private Context context;
   // private ArrayList<HistoryModel> historyModelArrayList = new ArrayList<>();
   private List<DeliveryBean.Order> deliveryBeanList = new ArrayList<>(  );
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
        holder.tvLocation.setText( deliveryBean.getLandmark() );
//        holder.tvItems.setText( (CharSequence) deliveryBean.getItem() );
        holder.tvOrderedOn.setText( deliveryBean.getOrderedDate() );
        holder.tvTotalAmount.setText( deliveryBean.getTotalCost() );
        // holder.tvRating.setText(historyModel.getRating());

        //String [] orderList ={"Selected Order,Pickup Order,Cancel Order"};


    }

    @Override
    public int getItemCount() {
        return deliveryBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * ButterKnife Code
         **/
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
        @BindView(R.id.tv_items)
        TextView tvItems;
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
       /* @BindView(R.id.tv_repeat_order)
        TextView tvRepeatOrder;
       */

        /**
         * ButterKnife Code
         **/
        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            address.setOnClickListener( this );
            List<String> orderList = new ArrayList<String>(  );
            orderList.add( "Received" );
            orderList.add( "Picked-up" );
            orderList.add( "Delivered" );
            dataAdapter = new ArrayAdapter<String>(context,R.layout.layout_spinner,orderList);
            dataAdapter.setDropDownViewResource(R.layout.layout_spinner );
            orderSpinnar.setAdapter( dataAdapter );
            orderSpinnar.setSelection(0,false);
            orderSpinnar.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();

                    home.spinnerList( position,item );
                 //   mcallBack.spinnerList( position, item );

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            } );

        }


        @OnClick({R.id.address})
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            DeliveryBean.Order deliveryBean = deliveryBeanList.get( position );

            mcallBack.alertdailog( position,deliveryBean );
          //  mcallBack.spinnerList( position,dataAdapter  );
        }
    }

}


