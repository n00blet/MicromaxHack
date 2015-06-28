package com.micromax.hack.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micromax.hack.R;
import com.micromax.hack.network.RestClient;
import com.micromax.hack.utils.Constants;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Rakshith on 27-06-2015.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail,mPassword;
    private Button mLogin;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLayout();
        initListener();
    }


    private void initLayout(){
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
    }

    private void initListener(){
mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        switch (view.getId()){
            case R.id.login:
                if(email!=null || !email.equals("") || password!=null || !password.equals(""))
                    authenticateUser(email,password);
                else
                    Toast.makeText(getApplicationContext(),"Please fill in ",Toast.LENGTH_SHORT).show();
        }
    }

    private void authenticateUser(String email, String password) {
        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("email",email);
        paramMap.put("name","Rakshith");
        paramMap.put("status","active");
        paramMap.put("gcmid","Ap134gajgjagjaag32123455");
        paramMap.put("loginid",email);
        paramMap.put("password",password);
        RequestParams params = new RequestParams(paramMap);
        Log.d("Params",":" + params);
        RestClient.post(Constants.LOGIN,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Object", ":" + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Array", ":" + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Success",":" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failure",":" + responseString);
            }
        });
    }


}
