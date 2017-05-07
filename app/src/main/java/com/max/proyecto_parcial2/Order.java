package com.max.proyecto_parcial2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Order extends AppCompatActivity {

    private TextView id;
    private TextView employee;
    private TextView client;
    private TextView delivery;
    private TextView address;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //match views with the layout
        id = (TextView) findViewById(R.id.id_order);
        employee = (TextView) findViewById(R.id.employee_name);
        client = (TextView) findViewById(R.id.client_name);
        delivery = (TextView) findViewById(R.id.delivery);
        address = (TextView) findViewById(R.id.address);
        back = (Button) findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent().setClass(Order.this, MainActivity_Employee.class);
                startActivity(orderIntent);
            }
        });

        Bundle myIntent = getIntent().getExtras();
        String value = " ";
        if(myIntent != null) {value = myIntent.getString("idOrder");}

        id.setText(value);

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/pedidos.r.php?id=" + value;

        JsonArrayRequest jsonrequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject order = (JSONObject) response.get(0);

                    String valueEmployee = order.getString("id_empleado");
                    String valueClient = order.getString("id_cliente");

                    setEmployee(valueEmployee);
                    setClient(valueClient);

                    String newFormat = order.getString("fecha_entrega");
                    delivery.setText(setDateOfDelivery(newFormat));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR VOLLEY", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonrequest);
    }

    void setEmployee(String value){
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/empleado.r.php?id_empleado=" + value;

        JsonArrayRequest jsonrequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject employeeInfo = (JSONObject) response.get(0);
                    employee.setText(employeeInfo.getString("nombre") + " " + employeeInfo.getString("apellido"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR VOLLEY", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonrequest);
    }

    void setClient(String value){
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/cliente.r.php?id_cliente=" + value;

        JsonArrayRequest jsonrequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject clientInfo = (JSONObject) response.get(0);
                    client.setText(clientInfo.getString("nombre") + " " + clientInfo.getString("apellido"));
                    address.setText(clientInfo.getString("direccion"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR VOLLEY", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonrequest);
    }

    String setDateOfDelivery(String date){
        String newFormat = date.substring(0,10);
        return newFormat;
    }
}
