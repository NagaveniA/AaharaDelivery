package com.example.aaharadelivery.NetworkUtils;


import com.example.aaharadelivery.Model.DeliveryBean;
import com.example.aaharadelivery.Model.LoginBean;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Codebele on 25-Aug-20.
 */
public interface Api {

    @POST("deliveryboySingin")
    Call<LoginBean> deliveryboySingin(@Header("Content-Type") String content_typ,@Body JsonObject body );

    @GET("deliveryList")
    Call<ServerResponse<DeliveryBean>>getdeliveryList(@Header("Accesstoken") String access_token);


    @POST("updateDelivaryStatus")
    Call<ServerResponse<String>>updateDelivaryStatus(@Header("Accesstoken") String access_token,@Body JsonObject body);

}
