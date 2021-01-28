package com.hsdroid.zomatoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    ListView lstResult;
    ProgressDialog pDialog;
    ArrayList<String> name;
    ArrayList<String> address;
    ArrayList<String> mobile;
    ArrayList<String> img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        lstResult = findViewById(R.id.lstShowResult);
        getResultMethod();
    }

    public void getResultMethod() {
        name = new ArrayList<>();
        address = new ArrayList<>();
        mobile = new ArrayList<>();
        img = new ArrayList<>();
        pDialog = new ProgressDialog(ResultActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        AndroidNetworking.get(ContentFilesPath.resultPath + ContentFilesPath.CityId + "&entity_type=city")
                .addHeaders("user-key", ContentFilesPath.userKey)
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray obj = response.getJSONArray("restaurants");
                    for (int i = 0; i < obj.length(); i++) {
                        JSONObject arr = obj.getJSONObject(i);
                        JSONObject ar = arr.getJSONObject("restaurant");
                        JSONObject locObj = ar.getJSONObject("location");
                        name.add(ar.getString("name"));
                        address.add(locObj.getString("address"));
                        mobile.add(ar.getString("phone_numbers"));
                        img.add(ar.getString("thumb"));
                    }
                    pDialog.dismiss();
                    lstResult.setAdapter(new CustomAdapter(getApplicationContext(), name, address, mobile, img));
                } catch (JSONException e) {
                    //  System.err.println(e);
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Network Problem", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                System.err.println(anError);
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Network Problem", Toast.LENGTH_LONG).show();
            }
        });

    }
}