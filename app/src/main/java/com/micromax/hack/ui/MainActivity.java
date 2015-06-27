package com.micromax.hack.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micromax.hack.R;
import com.micromax.hack.network.RestClient;
import com.micromax.hack.utils.FileUtils;
import com.micromax.hack.utils.Constants;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mUpload;
    private Button mChooseFile;
    private TextView mFilePath;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initListener();
    }


    private void initLayout(){
        mUpload = (Button) findViewById(R.id.upload_file);
        mChooseFile = (Button) findViewById(R.id.choose_file);
        mFilePath = (TextView) findViewById(R.id.path_text);
    }

    private void initListener(){
        mUpload.setOnClickListener(this);
        mChooseFile.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        String filePath = mFilePath.getText().toString().trim();
        switch (view.getId()){
            case R.id.choose_file:
                showChooser();
                break;
            case R.id.upload_file:
                uploadFileToServer(filePath);
                break;
        }
    }

    private void uploadFileToServer(String filePath) {
        Log.d("Path",":" + filePath);
        File file = new File(filePath) ;
        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("loginid","mani");
        paramMap.put("srcfilepath",filePath);
        RequestParams params = new RequestParams(paramMap);
        try{
            params.put("fileContent",file);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("File params",":" + params);
        RestClient.post(Constants.FILE_UPLOAD,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
               Log.d("Array",":" + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Object",":" + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Success", ":" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failure", ":" + responseString);
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, Constants.REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri=" + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            mFilePath.setText(path);
                        } catch (Exception e) {
                            Log.e("SelectorTestActivity", "File select error", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
