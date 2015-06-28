package com.micromax.hack.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micromax.hack.R;
import com.micromax.hack.model.FilePermission;
import com.micromax.hack.model.Metadata;
import com.micromax.hack.network.RestClient;
import com.micromax.hack.utils.Constants;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rakshith on 28-06-2015.
 */
public class SharedFileAdapter extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater mInflater = null;
    ArrayList<FilePermission> mFiles;
    boolean returnAnswer = false;

    public SharedFileAdapter(Activity activity,ArrayList<FilePermission> files){
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
        final ViewHolder holder;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.shared_files_list_item,parent,false);
            holder = new ViewHolder();
            holder.filename = (TextView) convertView.findViewById(R.id.file_name);
            holder.recievedPerson = (TextView) convertView.findViewById(R.id.recieved_person);
            holder.date = (TextView) convertView.findViewById(R.id.file_created_date);
            holder.status = (TextView) convertView.findViewById(R.id.file_status);
            holder.revokePermission = (Button) convertView.findViewById(R.id.revoke_permission);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        final String id = mFiles.get(position).getId();
        holder.filename.setText("Filename: " + mFiles.get(position).getFilename());
        holder.recievedPerson.setText("Reciever: " + mFiles.get(position).getRecieverid());
        holder.date.setText("Date: " + mFiles.get(position).getCreatedate());
        holder.status.setText("Status: " + mFiles.get(position).getStatus());
        holder.revokePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ans = revokePermission(id);
                if(ans){
                    holder.revokePermission.setVisibility(View.GONE);
                    holder.status.setText("Status: InActive");
                }
            }
        });


        return convertView;
    }

    public boolean revokePermission(String id){


        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("loginid","mani");
        paramMap.put("permissionid", id);
        RequestParams params = new RequestParams(paramMap);
        RestClient.post(Constants.REVOKE_PERMISSION, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Success", ":" + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Log.d("Success str", ":" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Response", ":" + responseString);
                if (responseString.equalsIgnoreCase("200")) {
                    returnAnswer = true;
                    Toast.makeText(mActivity,"Your permissions for this user will be revoked",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mActivity,"Somethiing went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
return returnAnswer;
    }

    static class ViewHolder{
        TextView filename;
        TextView recievedPerson;
        TextView date;
        TextView status;
        Button revokePermission;
    }
}
