package com.micromax.hack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micromax.hack.R;
import com.micromax.hack.adapter.SharedFileAdapter;
import com.micromax.hack.model.FilePermission;
import com.micromax.hack.network.RestClient;
import com.micromax.hack.utils.Constants;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Rakshith on 28-06-2015.
 */
public class SharedFilesActivity extends AppCompatActivity {

    private ListView sharedFilesList;
    private ArrayList<FilePermission> listOfFiles;
    private SharedFileAdapter sharedFileAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_files);
        sharedFilesList = (ListView) findViewById(R.id.shared_files_list);
        listOfFiles = new ArrayList<>();
        getSharedFiles();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SharedFilesActivity.this,HomeActivity.class));
        finish();
    }

    private void getSharedFiles(){
        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("loginid", "mani");
        RequestParams params = new RequestParams(paramMap);
        RestClient.post(Constants.GET_SHARED_FILES, params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.d("Response array", ":" + response);
                        if (response != null) {
                            int length = response.length();
                            for (int i = 0; i < length; i++) {
                                try {
                                    FilePermission files = new FilePermission();
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    files.setExtension(jsonObject.getString("extension"));
                                    files.setId(jsonObject.getString("_id"));
                                    files.setTime(jsonObject.getString("time"));
                                    files.setStatus(jsonObject.getString("status"));
                                    files.setCreatedate(createDate(jsonObject.getString("createdate")));
                                    files.setRecieverid(jsonObject.getString("recieverid"));
                                    files.setFilename(jsonObject.getString("filename"));
                                    files.setFileid(jsonObject.getString("fileid"));
                                    files.setLoginid(jsonObject.getString("loginid"));
                                    listOfFiles.add(files);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (listOfFiles.size() > 0) {
                                sharedFileAdapter = new SharedFileAdapter(SharedFilesActivity.this, listOfFiles);
                                sharedFilesList.setAdapter(sharedFileAdapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("Response string", ":" + responseString);
                    }
                }
        );

    }

    private String createDate(String timeinMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy");
        Date d = new Date();
        d.setTime(Long.parseLong(timeinMillis));
        return simpleDateFormat.format(d);
    }
}
