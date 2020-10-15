package com.aahara.aaharadelivery.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aahara.aaharadelivery.LoginActivity;
import com.aahara.aaharadelivery.Model.DeliveryBean;
import com.aahara.aaharadelivery.Model.HistoryModel;
import com.aahara.aaharadelivery.Model.OrderBean;
import com.aahara.aaharadelivery.NetworkUtils.Api;
import com.aahara.aaharadelivery.NetworkUtils.ApiClient;
import com.aahara.aaharadelivery.NetworkUtils.ServerResponse;
import com.aahara.aaharadelivery.R;
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


public class HomeFragment extends Fragment implements View.OnClickListener{

    private boolean isExpandedOrderList = true;
    private RecyclerView rvHistory;
    private HistoryAdapter historyAdaptar;
    private ArrayList<HistoryModel> historyModelArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager, layoutManager1;
    private ArrayList<OrderBean> orderBeanList = new ArrayList<>();
    List<String> orderList = new ArrayList<String>();
    private TextView address;
    TextView tvOrderSpinner, tvHistory, tv_location, logout;
    Context context;
    RecyclerView rvOrderDropDown;
    RelativeLayout rlSpinnerProject;

    ImageView ivOrderExpand;
    ProgressDialog loading;

    String accesss_token = "";
    private DeliveryBean deliveryBean;
    ArrayList<DeliveryBean.Order> deliveryBeans = new ArrayList<>();
    ArrayList<DeliveryBean.Item> deliveryBeansitem = new ArrayList<>();
    private UserSessionManager session;
    public Handler handler;


    public HomeFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session = new UserSessionManager(getContext());
        accesss_token = session.getUserDetails().get(UserSessionManager.KEY_accessToken);
        Log.d("accesss_token", accesss_token.toString());
        rvHistory = (RecyclerView) view.findViewById(R.id.rv_history);
        tvHistory = (TextView) view.findViewById(R.id.history);
        logout = (TextView) view.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        tvHistory.setOnClickListener(this);



        callApi();
        doTheAutoRefresh();
        final SwipeRefreshLayout pullToRefresh =view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApi();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void doTheAutoRefresh() {
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callApi();
                doTheAutoRefresh();
            }
        }, 50000);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void callApi() {
        Api api = ApiClient.getClient().create(Api.class);
//        loading = ProgressDialog.show(getContext(), "Loading.....", "wait....", false, false);
        Call<ServerResponse<DeliveryBean>> call = api.getdeliveryList(accesss_token);
        call.enqueue(new Callback<ServerResponse<DeliveryBean>>() {

            @Override
            public void onResponse(Call<ServerResponse<DeliveryBean>> call, Response<ServerResponse<DeliveryBean>> response) {
//                loading.cancel();
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
                        ArrayList<DeliveryBean.Order> order = new ArrayList<>();
                        initializeRecyclerView(order);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<DeliveryBean>> call, Throwable t) {
                loading.cancel();
            }
        });
    }

    private void initializeRecyclerView(ArrayList<DeliveryBean.Order> deliveryBean) {

        layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        historyAdaptar = new HistoryAdapter(getContext(), deliveryBean,this);
        rvHistory.setLayoutManager(layoutManager1);
        rvHistory.setAdapter(historyAdaptar);
    }

    public void callApi1(int i, String item, String orderId) {
        Api api = ApiClient.getClient().create(Api.class);
        JsonObject body = new JsonObject();
        body.addProperty("order_id", orderId);
        body.addProperty("order_status", item);
        Call<ServerResponse<String>> call = api.updateDelivaryStatus(accesss_token, body);
        call.enqueue(new Callback<ServerResponse<String>>() {

            @Override
            public void onResponse(Call<ServerResponse<String>> call, Response<ServerResponse<String>> response) {

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        Toast.makeText(getContext(), response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
                        Toast.makeText(getContext(), jObjError.getString("message"),  Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<String>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.logout:
                session.logoutUser();
                Intent logIntent = new Intent(getContext(), LoginActivity.class);
                logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                logIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logIntent);
                getActivity().finish();

        }
    }
}