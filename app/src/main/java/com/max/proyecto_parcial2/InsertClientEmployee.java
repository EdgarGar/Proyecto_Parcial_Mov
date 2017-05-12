package com.max.proyecto_parcial2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class InsertClientEmployee extends AppCompatActivity {

    EditText name_client;
    EditText lastname_client;
    EditText email_client;
    EditText address_client;

    String jsonResponse, jsonmessage;

    Button btn_add_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_client_employee);

        name_client = (EditText) findViewById(R.id.name_client);
        lastname_client = (EditText) findViewById(R.id.lastname_client);
        email_client = (EditText) findViewById(R.id.mail_client);
        address_client = (EditText) findViewById(R.id.address_client);

        btn_add_client = (Button) findViewById(R.id.add_client);

        btn_add_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_user();
            }
        });
    }

    public void insert_user(){

        if(name_client.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(lastname_client.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(email_client.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(address_client.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/cliente.c.php";
        url += "?nombre=" + name_client.getText().toString() + "&apellido=" + lastname_client.getText().toString() + "&mail=" + email_client.getText().toString() + "&direccion=" + address_client.getText().toString();


        final JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonResponse = response.getString("Codigo");
                    jsonmessage = response.getString("Mensaje");


                    if((jsonResponse.compareTo("01") == 0)){
                        Toast.makeText(getApplicationContext(), jsonmessage, Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent().setClass(InsertClientEmployee.this, ChooseClient.class);
                        startActivity(mainIntent);
                    }
                    if((jsonResponse.compareTo("04") == 0)){
                        Toast.makeText(getApplicationContext(), jsonmessage, Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent().setClass(InsertClientEmployee.this, ChooseClient.class);
                        startActivity(mainIntent);
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
