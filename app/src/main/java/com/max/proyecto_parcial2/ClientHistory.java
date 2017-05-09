package com.max.proyecto_parcial2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class ClientHistory extends AppCompatActivity {
    private ArrayList<ProductHistoryItem> listProduct = new ArrayList<>();
    private RecyclerView lv;
    private EditText day, month, year;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_history);

        lv = (RecyclerView) findViewById(R.id.history_recyclerview);
        lv.setLayoutManager(new LinearLayoutManager(this));

        day = (EditText) findViewById(R.id.in_his_day);
        month = (EditText) findViewById(R.id.in_his_month);
        year = (EditText) findViewById(R.id.in_his_year);
        update = (Button) findViewById(R.id.c_his_btn_update);

        updateList();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateList();
            }
        });
    }

    private void updateList() {
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/historialCompras.php";
        url += "?idc=" + UserVariables.getInstance().getClientID();
        if (!year.getText().toString().isEmpty()) { url += "&year=" + year.getText().toString(); }
        if (!month.getText().toString().isEmpty()) { url += "&month=" + month.getText().toString(); }
        if (!day.getText().toString().isEmpty()) { url += "&day=" + day.getText().toString(); }
        Log.d("Request: ", url);

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response: ", response.toString());
                        listProduct.clear();
                        listProduct = parserJSON_product_history.parseaArreglo(response);

                        Cardview_history_item arrayAdapter = new Cardview_history_item(getApplicationContext(), listProduct);
                        lv.setAdapter(arrayAdapter);

                        arrayAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error en: " + error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(product_json_array);
    }
}
