package com.hsdroid.zomatoapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText txtCityName;
    Button btnSearch;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCityName = findViewById(R.id.txtCityName);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sCityname = txtCityName.getText().toString();
                if (sCityname.equals("")) {
                    txtCityName.setError("Required");
                } else {
                    getIdMethod();
                }
            }
        });
    }

    public void getIdMethod() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        AndroidNetworking.get(ContentFilesPath.idPath + txtCityName.getText().toString().trim().toLowerCase())
                .addHeaders("user-key", ContentFilesPath.userKey)
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray obj = response.getJSONArray("location_suggestions");
                    JSONObject arr = obj.getJSONObject(0);
                    ContentFilesPath.CityId = arr.getInt("id");
                    pDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    System.err.println(e);
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