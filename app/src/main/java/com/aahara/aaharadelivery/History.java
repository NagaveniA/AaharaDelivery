
package com.aahara.aaharadelivery;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.os.Bundle;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import com.aahara.aaharadelivery.Model.HistoryModel;
        import com.aahara.aaharadelivery.Model.OrderBean;
        import com.aahara.aaharadelivery.adapters.OldOrderAdapter;


        import java.util.ArrayList;

public class History extends AppCompatActivity  {


    private boolean isExpandedOrderList = true;

    private RecyclerView rvHistory;
    private OldOrderAdapter oldOrderAdapter;
    private ArrayList<HistoryModel> historyModelArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager,layoutManager1;
    private ArrayList<OrderBean> orderBeanList = new ArrayList<>();


    private TextView address;
    TextView tvOrderSpinner;

    RecyclerView rvOrderDropDown;
    RelativeLayout rlSpinnerProject;

    ImageView ivOrderExpand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_history );

        rvHistory = (RecyclerView) findViewById(R.id.rv_history);
        initializeRecycler();
        //initOrderRecycler();

    }



   /*   private void initOrderRecycler() {
           orderBeanList.clear();
          layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
          rvOrderDropDown.setLayoutManager(layoutManager1);
          orderAdapter = new OrderAdapter(orderBeanList, this,this);
          orderBeanList.add(new OrderBean("1","Selected Order"));
          orderBeanList.add(new OrderBean("2","Pickup Order"));
          orderBeanList.add(new OrderBean("3","Cancel Order"));
          rvOrderDropDown.setAdapter(orderAdapter);
    }*/



    private void initializeRecycler() {
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rvHistory.setLayoutManager(layoutManager);
        historyModelArrayList.add(new HistoryModel("Roll Planet","Ballari",
                "1 × Paneer Roll,1 × Veggie Roll,1 × Paneer Roll","24 Dec 2019 at 6:36 PM","₹117.00","Address","available"));
        historyModelArrayList.add(new HistoryModel("Roll Planet","Ballari",
                "1 × Paneer Roll,1 × Veggie Roll,1 × Paneer Roll","24 Dec 2019 at 6:36 PM","₹117.00","Address","available"));
        historyModelArrayList.add(new HistoryModel("Roll Planet","Ballari",
                "1 × Paneer Roll,1 × Veggie Roll,1 × Paneer Roll","24 Dec 2019 at 6:36 PM","₹117.00","Address","no"));
        historyModelArrayList.add(new HistoryModel("Roll Planet","Ballari",
                "1 × Paneer Roll,1 × Veggie Roll,1 × Paneer Roll","24 Dec 2019 at 6:36 PM","₹117.00","Address","no"));

        oldOrderAdapter = new OldOrderAdapter(this,historyModelArrayList);
        rvHistory.setAdapter(oldOrderAdapter);

    }

   /* @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.rl_spinner_project})
    public void onClick(View view) {

        if (isExpandedOrderList) {
            ivOrderExpand.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_up));
            rvHistory.setVisibility(View.VISIBLE);
            isExpandedOrderList = false;
        } else {
            ivOrderExpand.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_down));
            rvHistory.setVisibility(View.GONE);
            isExpandedOrderList = true;
        }
    }*/


   /* @Override
    public void alertdailog(int position,DeliveryBean.Order deliveryBean) {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.popup_address, viewGroup, false);
        // Button btn = (Button) dialogView.findViewById(R.id.btn_done);
           *//* TextView tv_title_message = (TextView) dialogView.findViewById(R.id.title_message);
            TextView tv_description = (TextView) dialogView.findViewById(R.id.tv_success_text);
         *//*   //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
          *//*  btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    HomeActivity.this.finish();
                }
            });
*//*

    }*/


 /*   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void orderList(int position, String selected_item, TextView tvName, OrderBean dropDownModel) {
        tvOrderSpinner.setText(selected_item);
        rvOrderDropDown.setVisibility(View.GONE);
        if (!isExpandedOrderList) {
            ivOrderExpand.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_down));
            isExpandedOrderList = true;
        }
    }
*/
}
