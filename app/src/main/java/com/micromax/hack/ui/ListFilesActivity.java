package com.micromax.hack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micromax.hack.R;
import com.micromax.hack.adapter.MetadataAdapter;
import com.micromax.hack.model.Metadata;
import com.micromax.hack.network.RestClient;
import com.micromax.hack.utils.Constants;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Rakshith on 27-06-2015.
 */
public class ListFilesActivity extends AppCompatActivity {

    private ArrayList<Metadata> listOfFiles;
    private MetadataAdapter metadataAdapter;
    private ListView filesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);
        filesList = (ListView) findViewById(R.id.files_list);
        listOfFiles = new ArrayList<>();
        getUploadedFiles();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ListFilesActivity.this,HomeActivity.class));
        finish();
    }

    private void getUploadedFiles() {
        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("loginid","mani");
        RequestParams params = new RequestParams(paramMap);
        RestClient.post(Constants.GET_ALL_FILES, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Array", ":" + response);
                if (response != null) {
                    int length = response.length();
                    for (int i = 0; i < length; i++) {
                        try {
                            Metadata file = new Metadata();
                            JSONObject jsonObject = response.getJSONObject(i);
                            file.setFileName(jsonObject.getString("fileName"));
                            file.setFileId(jsonObject.getString("fileId"));
                            file.setMimeType(jsonObject.getString("mimeType"));
                            file.setCreatedate(createDate(jsonObject.getString("createdate")));
                            listOfFiles.add(file);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (listOfFiles.size() > 0) {
                        metadataAdapter = new MetadataAdapter(ListFilesActivity.this, listOfFiles);
                        filesList.setAdapter(metadataAdapter);
                    }
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Success", ":" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failure", ":" + responseString);
            }
        });
    }

    private String createDate(String timeinMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy");
        Date d = new Date();
        d.setTime(Long.parseLong(timeinMillis));
        return simpleDateFormat.format(d);
    }

}
