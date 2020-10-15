package com.aahara.aaharadelivery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aahara.aaharadelivery.LoginActivity;
import com.aahara.aaharadelivery.Model.DeliveryBean;
import com.aahara.aaharadelivery.Model.OrderHistoryBean;
import com.aahara.aaharadelivery.NetworkUtils.Api;
import com.aahara.aaharadelivery.NetworkUtils.ApiClient;
import com.aahara.aaharadelivery.NetworkUtils.ServerResponse;
import com.aahara.aaharadelivery.R;
import com.aahara.aaharadelivery.SessionManagers.UserSessionManager;
import com.aahara.aaharadelivery.adapters.HistoryAdapter;
import com.aahara.aaharadelivery.adapters.OrderHistoryAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment implements View.OnClickListener{
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvOrderHistory;
    private OrderHistoryAdapter orderHistoryAdaptar;
    TextView logout;
    String accesss_token = "";
    private OrderHistoryBean orderHistoryBean;
    ArrayList<OrderHistoryBean.Order> orderHistoryBeans = new ArrayList<>();
    //ArrayList<OrderHistoryBean.Item> deliveryBeansitem = new ArrayList<>();
    private UserSessionManager session;
    public Handler handler;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session = new UserSessionManager(getContext());
        accesss_token = session.getUserDetails().get(UserSessionManager.KEY_accessToken);
        Log.d("accesss_token", accesss_token.toString());
        rvOrderHistory = (RecyclerView) view.findViewById(R.id.rv_orderhistory);

        logout = (TextView) view.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        callApi();
    }
    public void callApi() {
        Api api = ApiClient.getClient().create(Api.class);
//        loading = ProgressDialog.show(getContext(), "Loading.....", "wait....", false, false);
        Call<ServerResponse<OrderHistoryBean>> call = api.getOrderHistoryList(accesss_token);
        call.enqueue(new Callback<ServerResponse<OrderHistoryBean>>() {
            @Override
            public void onResponse(Call<ServerResponse<OrderHistoryBean>> call, Response<ServerResponse<OrderHistoryBean>> response) {
//                loading.cancel();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        OrderHistoryBean ohBeanse = response.body().getData();
                        orderHistoryBeans = (ArrayList<OrderHistoryBean.Order>) ohBeanse.getOrder();

                        Log.d("OrderHistoryList", orderHistoryBeans.toString());

                        initializeRecyclerView((ArrayList<OrderHistoryBean.Order>) ohBeanse.getOrder());
                    } else {
                        //   showToast(getApplicationContext(), getResources().getString(R.string.something_wrong));
                    }

                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
                        ArrayList<OrderHistoryBean.Order> order = new ArrayList<>();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<OrderHistoryBean>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
               // loading.cancel();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    private void initializeRecyclerView(ArrayList<OrderHistoryBean.Order> orderHistoryBean) {

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        orderHistoryAdaptar = new OrderHistoryAdapter(getContext(), orderHistoryBean,this);
        rvOrderHistory.setLayoutManager(layoutManager);
        rvOrderHistory.setAdapter(orderHistoryAdaptar);
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