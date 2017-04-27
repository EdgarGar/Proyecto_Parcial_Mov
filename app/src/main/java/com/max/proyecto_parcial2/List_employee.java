package com.max.proyecto_parcial2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import java.util.ArrayList;

public class List_employee extends AppCompatActivity {
    private final String TAG = "ListEmployee";
    ArrayList<Employee> listEmployee = new ArrayList<>();
    RecyclerView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);

        lv = (RecyclerView) findViewById(R.id.employee_list);
        lv.setLayoutManager(new LinearLayoutManager(this));

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/empleado.r.php";

        JsonArrayRequest employee_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listEmployee = parserJSON_employee.parseaArreglo(response);

                        Cardview_employee arrayAdapter = new Cardview_employee(getApplicationContext(), listEmployee);
                        lv.setAdapter(arrayAdapter);

                        arrayAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error en: " + error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(employee_json_array);
    }
}
