package com.example.aaharadelivery.NetworkUtils;

import android.util.Log;


import com.example.aaharadelivery.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * Created by Codebele on 08-Aug-19.
 */

public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        if (!new ServiceManager( App.pInstance).isNetworkAvailable()) {
            Log.v("exception","no internet");
            throw (new IOException("No Internet"));

        } else {
            try {
                response = chain.proceed(chain.request());
            }
            catch (IOException e){
                Log.v("exception",e.getMessage());
                throw (new IOException("No Internet"));
            }



        }
        return response;
    }
}




