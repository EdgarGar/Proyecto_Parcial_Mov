package com.max.proyecto_parcial2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GraphsReport_SoldProduct extends AppCompatActivity {

    BarChart barchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs_report__sold_product);

        barchart = (BarChart) findViewById(R.id.Bargraph);

        final ArrayList<BarEntry> BarEntries= new ArrayList<>();
        final ArrayList<String> ProductName = new ArrayList<>();

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/producto.r.php";

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject obj=null;
                        BarEntry producto = null;
                        BarEntries.clear();
                        ProductName.clear();

                        try {
                            for(int i = 0;i<response.length();i++) {

                                obj = response.getJSONObject(i);

                                BarEntries.add(new BarEntry(Float.parseFloat(response.getJSONObject(i).getString("contador")),i));
                                ProductName.add(response.getJSONObject(i).getString("nombre"));
                            }
                            BarDataSet barDataSet = new BarDataSet(BarEntries, "Products");

                            BarData _Products = new BarData(ProductName, barDataSet);
                            barchart.setData(_Products);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

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
