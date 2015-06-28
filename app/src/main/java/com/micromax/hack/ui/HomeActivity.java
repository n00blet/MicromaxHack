package com.micromax.hack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.micromax.hack.R;

/**
 * Created by Rakshith on 28-06-2015.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mViewFiles,mSharedFiles,mUploadFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initLayout();
        initListeners();
    }

    private void initLayout(){
        mViewFiles = (Button) findViewById(R.id.view_files);
        mSharedFiles = (Button) findViewById(R.id.change_permissions);
        mUploadFile = (Button) findViewById(R.id.upload_file);
    }

    private void initListeners(){
        mViewFiles.setOnClickListener(this);
        mSharedFiles.setOnClickListener(this);
        mUploadFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.view_files:
                startActivity(new Intent(HomeActivity.this,ListFilesActivity.class));
                finish();
                break;
            case R.id.change_permissions:
                startActivity(new Intent(HomeActivity.this,SharedFilesActivity.class));
                finish();
                break;
            case R.id.upload_file:
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                break;
        }
    }
}
