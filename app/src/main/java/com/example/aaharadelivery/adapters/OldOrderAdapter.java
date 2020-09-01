package com.example.aaharadelivery.adapters;

        import android.R.layout;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Build;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.SpinnerAdapter;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.RequiresApi;
        import androidx.core.content.ContextCompat;
        import androidx.recyclerview.widget.RecyclerView;


        import com.example.aaharadelivery.Home;
        import com.example.aaharadelivery.Interface.AlertDailog;
        import com.example.aaharadelivery.Model.HistoryModel;
        import com.example.aaharadelivery.R;
        import android.widget.ArrayAdapter;

        import java.util.ArrayList;
        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import butterknife.OnClick;

public class OldOrderAdapter extends RecyclerView.Adapter<OldOrderAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HistoryModel> historyModelArrayList = new ArrayList<>();
    AlertDailog mcallBack;


    public OldOrderAdapter(Context context, ArrayList<HistoryModel> historyModelArrayList/*, AlertDailog mcallBack*/) {
        this.context = context;
        this.historyModelArrayList = historyModelArrayList;
        //this.mcallBack = mcallBack;
    }
//    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyModelArrayList,Home home) {
//        this.context = context;
//        this.historyModelArrayList = historyModelArrayList;
//        this.home = home;
//       // this.alertDialog = alertDialog;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_history_order_list, parent, false );
        return new MyViewHolder( view );
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HistoryModel historyModel = historyModelArrayList.get( position );
        holder.tvShopName.setText( historyModel.getRestaurants_name() );
        holder.tvLocation.setText( historyModel.getLocation() );
        holder.tvItems.setText( historyModel.getItems() );
        holder.tvOrderedOn.setText( historyModel.getDate() );
        holder.tvTotalAmount.setText( historyModel.getTotal_amt() );
        // holder.tvRating.setText(historyModel.getRating());

        //String [] orderList ={"Selected Order,Pickup Order,Cancel Order"};

        List<String> orderList = new ArrayList<String>(  );
       /* orderList.add( "Received" );
        orderList.add( "Picked-up" );
     */   orderList.add( "Delivered" );

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item,orderList);
        dataAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );
        holder.orderSpinnar.setAdapter( dataAdapter );

    }

    @Override
    public int getItemCount() {
        return historyModelArrayList.size();
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
        }

        @OnClick({R.id.address})
        @Override
        public void onClick(View v) {
            /*int position = getAdapterPosition();
            mcallBack.alertdailog(position,);*/
        }
    }
}
