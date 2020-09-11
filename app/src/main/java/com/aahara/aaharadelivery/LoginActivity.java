package com.aahara.aaharadelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aahara.aaharadelivery.Model.LoginBean;
import com.aahara.aaharadelivery.NetworkUtils.Api;
import com.aahara.aaharadelivery.NetworkUtils.ApiClient;
import com.aahara.aaharadelivery.SessionManagers.UserSessionManager;
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


    }

    void initView() {

        im_login = (ImageView) findViewById(R.id.login);
        im_login.setOnClickListener(this);
    }

    private boolean validate() {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {
            tvUserError.setVisibility(View.VISIBLE);
            tvUserError.setText("Please Enter Correct Email");

            et_email.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(et_password.getText().toString())) {
            tvPassError.setVisibility(View.VISIBLE);
            tvPassError.setText("Please Enter Correct Password");
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
                        wrongMail.setVisibility(View.VISIBLE);
                        // wrongPassword.setVisibility( View.VISIBLE );
                        // showToast(getApplicationContext(), jObjError.getString("message"));

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


}
