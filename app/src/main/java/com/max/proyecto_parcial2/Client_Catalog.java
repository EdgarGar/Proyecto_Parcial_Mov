package com.max.proyecto_parcial2;

// Hecho por Edgar Adrián y Arthur Alves

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class Client_Catalog extends AppCompatActivity {
    private RecyclerView lv;
    private ArrayList<Product> listProduct = new ArrayList<>();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__catalog);

        lv = (RecyclerView) findViewById(R.id.client_catalog_recyclerview);
        lv.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = newRequestQueue(getApplicationContext());

        UserVariables.getInstance().checkEmpty();

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/producto.r.php";

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listProduct = parserJSON_products.parseaArreglo(response);

                        Cardview_products arrayAdapter = new Cardview_products(getApplicationContext(), listProduct);
                        lv.setAdapter(arrayAdapter);

                        arrayAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error en: " + error.getMessage());
            }
        });
        requestQueue.add(product_json_array);

        Button btn_client_cart = (Button) findViewById(R.id.btn_client_cart);

        btn_client_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(Client_Catalog.this, Cart.class);
                startActivity(intent);
            }
        });
    }
}
