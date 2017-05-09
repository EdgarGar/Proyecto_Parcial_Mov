package com.max.proyecto_parcial2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class InsertProduct extends AppCompatActivity {

    EditText name;
    EditText urlImage;
    EditText stock;
    EditText price;

    String jsonResponse, jsonmessage;

    Button btn_add_product;

    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/producto.c.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);

        name = (EditText) findViewById(R.id.name_product);
        urlImage = (EditText) findViewById(R.id.image_product);
        stock = (EditText) findViewById(R.id.stock_product);
        price = (EditText) findViewById(R.id.price_product);

        btn_add_product = (Button) findViewById(R.id.add_product);

        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_product();
            }
        });
    }

    public void insert_product(){

        if(name.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(urlImage.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(stock.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(price.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }

        url += "?nombre=" + name.getText().toString() + "&imagen=" + urlImage.getText().toString() + "&stock=" + stock.getText().toString() + "&precio=" + price.getText().toString();


        final JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonResponse = response.getString("Code");
                    jsonmessage = response.getString("Message");


                    if((jsonResponse.compareTo("01") == 0)){
                        Toast.makeText(getApplicationContext(), jsonmessage, Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent().setClass(InsertProduct.this, OptionsAdministrator.class);
                        startActivity(mainIntent);
                    }
                    if((jsonResponse.compareTo("04") == 0)){
                        Toast.makeText(getApplicationContext(), jsonmessage, Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent().setClass(InsertProduct.this, OptionsAdministrator.class);
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
