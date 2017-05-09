package com.max.proyecto_parcial2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphsReport_Comission extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<ProductHistoryItem> allProductsList;
    ArrayList<BarEntry> BarEntries;
    ArrayList<String> ProductName;
    BarChart barchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs_report__comission);

        barchart = (BarChart) findViewById(R.id.graph_commission);
        YAxis y = barchart.getAxisLeft();
        y.setAxisMinValue(0);

        allProductsList = new ArrayList<>();
        BarEntries= new ArrayList<>();
        ProductName = new ArrayList<>();

        Spinner spinner = (Spinner) findViewById(R.id.drop_down_selector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.graph_dropdown_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        BarEntries.clear();
        ProductName.clear();
        switch (position) {
            case 0:
//                updateList();
                selectDay();
                break;
            case 1:
//                updateList();
                selectMonth();
                break;
            case 2:
//                updateList();
                selectYear();
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) { }

    private void updateList() {
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/historialVentas.php";
        url += "?ide=" + UserVariables.getInstance().getEmployeeID();

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response: ", response.toString());
                        allProductsList = parserJSON_product_history.parseaArreglo(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error en: " + error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(product_json_array);
    }

    private void selectDay() {
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/historialVentas.php";
        url += "?ide=" + UserVariables.getInstance().getEmployeeID();

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response: ", response.toString());
                        allProductsList = parserJSON_product_history.parseaArreglo(response);

                        HashMap<String, Float> commissions = new HashMap<String, Float>();

                        for (int i = 0; i < allProductsList.size(); i++) {
                            if (!commissions.containsKey(allProductsList.get(i).getDay())) {
                                commissions.put(allProductsList.get(i).getDay(), allProductsList.get(i).getPrice() * 0.05f);
                                ProductName.add(allProductsList.get(i).getDay() + "/" + allProductsList.get(i).getMonth() + "/" + allProductsList.get(i).getYear());
                            } else {
                                commissions.put(allProductsList.get(i).getDay(), commissions.get(allProductsList.get(i).getDay()) + (allProductsList.get(i).getPrice() * 0.05f));
                            }
                        }

                        int i = 0;
                        for (Map.Entry<String, Float> entry : commissions.entrySet()) {
                            BarEntries.add(new BarEntry(entry.getValue(), i));
                            i++;
                        }

                        Log.d("Debug: ", allProductsList.size() + ", " + BarEntries.size() + " "+ ProductName.size());
                        BarDataSet barDataSet = new BarDataSet(BarEntries, "Commission by day");

                        BarData _Products = new BarData(ProductName, barDataSet);
                        barchart.setData(_Products);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error en: " + error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(product_json_array);
    }

    private void selectMonth() {
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/historialVentas.php";
        url += "?ide=" + UserVariables.getInstance().getEmployeeID();

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response: ", response.toString());
                        allProductsList = parserJSON_product_history.parseaArreglo(response);

                        HashMap<String, Float> commissions = new HashMap<String, Float>();

                        for (int i = 0; i < allProductsList.size(); i++) {
                            if (!commissions.containsKey(allProductsList.get(i).getMonth())) {
                                commissions.put(allProductsList.get(i).getMonth(), allProductsList.get(i).getPrice() * 0.05f);
                                ProductName.add(allProductsList.get(i).getMonth() + "/" + allProductsList.get(i).getYear());
                            } else {
                                commissions.put(allProductsList.get(i).getMonth(), commissions.get(allProductsList.get(i).getMonth()) + (allProductsList.get(i).getPrice() * 0.05f));
                            }
                        }

                        int i = 0;
                        for (Map.Entry<String, Float> entry : commissions.entrySet()) {
                            BarEntries.add(new BarEntry(entry.getValue(), i));
                            i++;
                        }

                        Log.d("Debug: ", allProductsList.size() + ", " + BarEntries.size() + " "+ ProductName.size());
                        BarDataSet barDataSet = new BarDataSet(BarEntries, "Commission by month");

                        BarData _Products = new BarData(ProductName, barDataSet);
                        barchart.setData(_Products);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error en: " + error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(product_json_array);
    }

    private void selectYear() {
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/historialVentas.php";
        url += "?ide=" + UserVariables.getInstance().getEmployeeID();

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response: ", response.toString());
                        allProductsList = parserJSON_product_history.parseaArreglo(response);

                        HashMap<String, Float> commissions = new HashMap<String, Float>();

                        for (int i = 0; i < allProductsList.size(); i++) {
                            if (!commissions.containsKey(allProductsList.get(i).getYear())) {
                                commissions.put(allProductsList.get(i).getYear(), allProductsList.get(i).getPrice() * 0.05f);
                                ProductName.add(allProductsList.get(i).getYear());
                            } else {
                                commissions.put(allProductsList.get(i).getYear(), commissions.get(allProductsList.get(i).getYear()) + (allProductsList.get(i).getPrice() * 0.05f));
                            }
                        }

                        int i = 0;
                        for (Map.Entry<String, Float> entry : commissions.entrySet()) {
                            BarEntries.add(new BarEntry(entry.getValue(), i));
                            i++;
                        }

                        Log.d("Debug: ", allProductsList.size() + ", " + BarEntries.size() + " "+ ProductName.size());
                        BarDataSet barDataSet = new BarDataSet(BarEntries, "Commission by month");

                        BarData _Products = new BarData(ProductName, barDataSet);
                        barchart.setData(_Products);
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
