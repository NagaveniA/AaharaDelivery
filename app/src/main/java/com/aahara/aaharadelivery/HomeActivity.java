package com.aahara.aaharadelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aahara.aaharadelivery.Interface.AlertDailog;
import com.aahara.aaharadelivery.Model.DeliveryBean;
import com.aahara.aaharadelivery.Model.HistoryModel;
import com.aahara.aaharadelivery.Model.OrderBean;
import com.aahara.aaharadelivery.NetworkUtils.Api;
import com.aahara.aaharadelivery.NetworkUtils.ApiClient;
import com.aahara.aaharadelivery.NetworkUtils.ServerResponse;
import com.aahara.aaharadelivery.SessionManagers.UserSessionManager;
import com.aahara.aaharadelivery.adapters.HistoryAdapter;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity implements AlertDailog, View.OnClickListener {


    private boolean isExpandedOrderList = true;

    private RecyclerView rvHistory;
    private HistoryAdapter historyAdaptar;
    private ArrayList<HistoryModel> historyModelArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager, layoutManager1;
    private ArrayList<OrderBean> orderBeanList = new ArrayList<>();

    List<String> orderList = new ArrayList<String>();


    private TextView address;
    TextView tvOrderSpinner, tvHistory, tv_location, logout;

    RecyclerView rvOrderDropDown;
    RelativeLayout rlSpinnerProject;

    ImageView ivOrderExpand;
    ProgressDialog loading;

    String accesss_token = "";
    private DeliveryBean deliveryBean;
    ArrayList<DeliveryBean.Order> deliveryBeans = new ArrayList<>();
    ArrayList<DeliveryBean.Item> deliveryBeansitem = new ArrayList<>();
    private UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        session = new UserSessionManager(getApplicationContext());
        accesss_token = session.getUserDetails().get(UserSessionManager.KEY_accessToken);
        Log.d("accesss_token", accesss_token.toString());

        rvHistory = (RecyclerView) findViewById(R.id.rv_history);
        tvHistory = (TextView) findViewById(R.id.history);
        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(this);
        tvHistory.setOnClickListener(this);

        callApi();
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApi();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void alertdailog(int position, DeliveryBean.Order deliveryBean) {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.popup_address, viewGroup, false);
        // Button btn = (Button) dialogView.findViewById(R.id.btn_done);
        TextView tv_location = (TextView) dialogView.findViewById(R.id.location);
        TextView tv_LandMark = (TextView) dialogView.findViewById(R.id.land_mark);
        TextView tv_MobileNumber = (TextView) dialogView.findViewById(R.id.mobile_number);

        tv_location.setText(deliveryBean.getAddressName());
        tv_LandMark.setText(deliveryBean.getLandmark());
        tv_MobileNumber.setText(deliveryBean.getUserMobile());
        //  TextView tv_description = (TextView) dialogView.findViewById(R.id.tv_success_text);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();


    }

    @Override
    public void spinnerList(int position, String item) {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.history:
                Intent intent = new Intent(this, History.class);
                startActivity(intent);
                break;
            case R.id.logout:
                session.logoutUser();
                Intent logIntent = new Intent(HomeActivity.this, LoginActivity.class);
                logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                logIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logIntent);
                finish();

        }
    }

    public void callApi() {
        Api api = ApiClient.getClient().create(Api.class);
        loading = ProgressDialog.show(this, "Loading.....", "wait....", false, false);

        //  Call<ServerResponse<DeliveryBean>> call = api.getdeliveryList("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.IjIi.D_pVGn5-G7FvW-yowpy3EtreDlFQ-N7xd0PrPB0WZ3M");
        Call<ServerResponse<DeliveryBean>> call = api.getdeliveryList(accesss_token);
        // Log.d("accesss_token",accesss_token.toString());

        call.enqueue(new Callback<ServerResponse<DeliveryBean>>() {

            @Override
            public void onResponse(Call<ServerResponse<DeliveryBean>> call, Response<ServerResponse<DeliveryBean>> response) {
                loading.cancel();
                if (response.isSuccessful()) {
                    if (response.body() != null) {


                        DeliveryBean deliveryBeanse = response.body().getData();
                        deliveryBeans = (ArrayList<DeliveryBean.Order>) deliveryBeanse.getOrder();

                        Log.d("deliveryList", deliveryBeans.toString());

                        initializeRecyclerView((ArrayList<DeliveryBean.Order>) deliveryBeanse.getOrder());
                    } else {
                        //   showToast(getApplicationContext(), getResources().getString(R.string.something_wrong));
                    }

                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
                        //   showToast(getApplicationContext(), jObjError.getString("message"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //    showToast(getApplicationContext(), getResources().getString(R.string.something_wrong));

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }
            }


            @Override
            public void onFailure(Call<ServerResponse<DeliveryBean>> call, Throwable t) {
                loading.cancel();
                // showToast(getApplicationContext(),t.toString());

            }
        });
    }


    private void initializeRecyclerView(ArrayList<DeliveryBean.Order> deliveryBean) {

        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        historyAdaptar = new HistoryAdapter(this, deliveryBean, this, this);
        rvHistory.setLayoutManager(layoutManager1);
        rvHistory.setAdapter(historyAdaptar);


    }





    public void callApi1(int i,String item,String orderId) {
        Api api = ApiClient.getClient().create(Api.class);
        JsonObject body = new JsonObject();
        body.addProperty("order_id", orderId);
        body.addProperty("order_status", item);
        Call<ServerResponse<String>> call = api.updateDelivaryStatus(accesss_token, body);
        call.enqueue(new Callback<ServerResponse<String>>() {

            @Override
            public void onResponse(Call<ServerResponse<String>> call, Response<ServerResponse<String>> response) {
//                loading.cancel();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        Toast.makeText(getApplicationContext(), response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
                        Toast.makeText(getApplicationContext(), jObjError.getString("message"),  Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //    showToast(getApplicationContext(), getResources().getString(R.string.something_wrong));

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }
            }


            @Override
            public void onFailure(Call<ServerResponse<String>> call, Throwable t) {
//                loading.cancel();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                // showToast(getApplicationContext(),t.toString());

            }
        });
    }


}
