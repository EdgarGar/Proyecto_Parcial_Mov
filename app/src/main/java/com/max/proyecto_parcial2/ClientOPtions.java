package com.max.proyecto_parcial2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientOPtions extends AppCompatActivity {

    String jsonResponse, jsonmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_options);

        Button select = (Button) findViewById(R.id.btn_select_client);
        Button delete = (Button) findViewById(R.id.btn_delete_client);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent().setClass(ClientOPtions.this, MainActivity_Employee.class);
                startActivity(listIntent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_user();
                Intent listIntent = new Intent().setClass(ClientOPtions.this, ChooseClient.class);
                startActivity(listIntent);
            }
        });

    }

    void delete_user(){
        String value = UserVariables.getInstance().getClientID();

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/cliente.d.php?id=" + value;

        final JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonResponse = response.getString("Codigo");
                    jsonmessage = response.getString("Mensaje");


                    if((jsonResponse.compareTo("01") == 0)){
                        Toast.makeText(getApplicationContext(), jsonmessage, Toast.LENGTH_LONG).show();
                    }
                    if((jsonResponse.compareTo("04") == 0)){
                        Toast.makeText(getApplicationContext(), jsonmessage, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonrequest);
    }
}
