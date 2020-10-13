package com.aahara.aaharadelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aahara.aaharadelivery.Model.LoginBean;
import com.aahara.aaharadelivery.NetworkUtils.Api;
import com.aahara.aaharadelivery.NetworkUtils.ApiClient;
import com.aahara.aaharadelivery.SessionManagers.UserSessionManager;
import com.aahara.aaharadelivery.app.Config;
import com.aahara.aaharadelivery.utilities.NotificationUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView im_login;
    @BindView(R.id.usererror)
    TextView tvUserError;
    @BindView(R.id.user_email)
    EditText et_email;

    @BindView(R.id.wrong_mail)
    TextView wrongMail;

    @BindView(R.id.password)
    EditText et_password;

    @BindView(R.id.password_error)
    TextView tvPassError;
    @BindView(R.id.wrong_password)
    TextView wrongPassword;

    ProgressDialog loading;
    private Context context;
    UserSessionManager session;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String deviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        session = new UserSessionManager(getApplicationContext());
        if (session.isUserLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        initView();

        textWatcher();
        session = new UserSessionManager(getApplicationContext());
        firebse_push_notification();

    }

    void initView() {

        im_login = (ImageView) findViewById(R.id.login);
        im_login.setOnClickListener(this);
    }

    private void firebse_push_notification() {
        /////firebase setup

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                deviceToken = instanceIdResult.getToken();
                Log.e("deviceToken", deviceToken);
                // Saving reg id to shared preferences
                storeRegIdInPref(deviceToken);

                // sending reg id to your server
                sendRegistrationToServer(deviceToken);
            }
        });


        // txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        //  txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            // txtRegId.setText("Firebase Reg Id: " + regId);
        } else {
            // txtRegId.setText("Firebase Reg Id is not received yet!");
        }

    }

    private boolean validate() {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {
            tvUserError.setVisibility(View.VISIBLE);
            tvUserError.setText("Enter email");

            et_email.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(et_password.getText().toString())) {
            tvPassError.setVisibility(View.VISIBLE);
            tvPassError.setText("Enter password");
            et_password.requestFocus();
            return false;

        }
        return true;

    }

    private void textWatcher() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvUserError.setVisibility(View.GONE);
                tvPassError.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        et_email.addTextChangedListener(tw);
        et_password.addTextChangedListener(tw);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login:
                if (validate()) {

                    callApi();
                }/*else {

                    Intent intent = new Intent( this, HomeActivity.class );
                    startActivity( intent );
                    break;
                }*/
        }
    }

    private void callApi() {
        Api api = ApiClient.getClient().create(Api.class);
        JsonObject body = new JsonObject();
        body.addProperty("email_mobile", et_email.getText().toString());
        body.addProperty("password", et_password.getText().toString());
        body.addProperty("device_token", deviceToken);
        loading = ProgressDialog.show(this, "Signing In.....", "wait....", false, false);
        Call<LoginBean> call = api.deliveryboySingin("application/json", body);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                loading.cancel();
                if (response.isSuccessful()) {
                    LoginBean loginBean = response.body();
                    Intent i;
                    session.createUserLoginSession(response.body().getData().getAccessToken());
                    Log.e("access_token", loginBean.getData().getAccessToken());
                    i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();


                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);

                        if ((jObjError.getString("message")).equals("User doesn't exist"))
                        {
                            tvUserError.setVisibility(View.VISIBLE);
                            tvUserError.setText(jObjError.getString("message"));
                        }
                        else {
                            tvPassError.setVisibility(View.VISIBLE);
                            tvPassError.setText(jObjError.getString("message"));
                        }

                        System.out.println(jObjError.getString("message")+"*****************************");
                        System.out.println(error_message+"+++++++++++++++++++++++++++++++");
                        // wrongPassword.setVisibility( View.VISIBLE );
                        //Toast.makeText(getApplicationContext(), jObjError.getString("message"),Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //  showToast(getApplicationContext(), getResources().getString(R.string.something_wrong));

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }
            }


            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                loading.cancel();
                // showToast(getApplicationContext(), t.toString());

            }
        });

    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
        Log.e(TAG, "leaving shared preference: " + token);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }
}
