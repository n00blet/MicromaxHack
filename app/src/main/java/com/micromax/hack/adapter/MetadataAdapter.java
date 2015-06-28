package com.micromax.hack.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micromax.hack.R;
import com.micromax.hack.model.Metadata;
import com.micromax.hack.network.RestClient;
import com.micromax.hack.utils.Constants;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rakshith on 27-06-2015.
 */
public class MetadataAdapter extends BaseAdapter {

    String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    String  filePath = "/sdcard/MaxS/";
    private Activity mActivity;
    private LayoutInflater mInflater = null;
    ArrayList<Metadata> mFiles;
    Button cancelButton,shareButton;
    EditText senderId,expirationTime;

    public MetadataAdapter(Activity activity,ArrayList<Metadata> files){
        this.mActivity = activity;
        this.mFiles = files;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return mFiles.size();
    }


    @Override
    public Object getItem(int position) {
        return mFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.files_list_item,parent,false);
            holder = new ViewHolder();
            holder.filename = (TextView) convertView.findViewById(R.id.file_name);
            holder.mimetype = (TextView) convertView.findViewById(R.id.file_type);
            holder.date = (TextView) convertView.findViewById(R.id.file_created_date);
            holder.viewFile = (Button) convertView.findViewById(R.id.view_file);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        final String fileId = mFiles.get(position).getFileId();
        final String fileName = mFiles.get(position).getFileName();
        final String mimetype = mFiles.get(position).getMimeType();
        final String date = mFiles.get(position).getCreatedate();
        holder.filename.setText(fileName);
        holder.mimetype.setText(mimetype);
        holder.date.setText(date);
        holder.viewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFileFromServer(fileId);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createShareDialog(fileId,fileName,mimetype);
            }
        });

        return convertView;
    }



    private void createShareDialog(final String id,final String filename,final String mimetype){
        final Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.share_file_to);
        dialog.setTitle("Share File With");
        cancelButton = (Button) dialog.findViewById(R.id.dialogCancelOK);
        shareButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        senderId = (EditText) dialog.findViewById(R.id.senderId);
        expirationTime = (EditText) dialog.findViewById(R.id.time);

        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
             Editable destination = senderId.getText();
             Editable expTime = expirationTime.getText();
            @Override
            public void onClick(View view) {
                Log.d("DSt",":" + destination.toString());
                Log.d("exp",":" + expTime.toString());
                if(destination!=null && !destination.equals(""))
                    sendFileId(id,filename,mimetype,destination.toString(),expTime.toString());
                else{
                    Toast.makeText(mActivity,"Please enter sender id",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void sendFileId(String fileid,String filename,String mimetype,String destination,String expiration) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("loginid", "mani");
        paramMap.put("fileid",fileid);
        paramMap.put("filename",filename);
        paramMap.put("extension",mimetype);
        paramMap.put("time",expiration);
        paramMap.put("recieverid", destination);
        RequestParams params = new RequestParams(paramMap);
        Log.d("Params", ":" + params);
        RestClient.post(Constants.SHARE_TO, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Success", ":" + response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failure", ":" + responseString);
            }
        });
    }

    private void getFileFromServer(final String fileId){
        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("loginid","mani");
        paramMap.put("fileid", fileId);
        RequestParams params = new RequestParams(paramMap);

        RestClient.post(Constants.GET_FILE_BY_ID, params, new FileAsyncHttpResponseHandler(mActivity) {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Log.d("Failre", ":" + file);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                FileInputStream fileInputStream = null;
                try {

                    FileInputStream fis = new FileInputStream(file);
                    FileOutputStream fos = new FileOutputStream(filePath + "max.bmp ");
                    int ch;
                    while ((ch = fis.read()) != -1) {
                        fos.write((char) ch);
                    }
                    fis.close();
                    fos.close();


                    System.out.println("Done");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("Success", ":" + file.getName());
                Log.d("Success", ":" + file.getTotalSpace());
            }
        });

    }

    static class ViewHolder{
        TextView filename;
        TextView mimetype;
        TextView date;
        Button viewFile;
    }
}
