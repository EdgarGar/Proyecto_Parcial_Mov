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

public class MainActivity_Administrator extends AppCompatActivity {

    EditText name_employee;
    EditText lastname_employee;
    EditText phone_employee;
    EditText email_employee;
    EditText password_employee;
    EditText username_employee;
    EditText role_employee;

    String jsonResponse, jsonmessage;

    Button btn_add_user, btn_list_employee;

    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/empleado.c.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrator);

        name_employee = (EditText) findViewById(R.id.name_employee);
        lastname_employee = (EditText) findViewById(R.id.lastname_employee);
        phone_employee = (EditText) findViewById(R.id.phone_employee);
        email_employee = (EditText) findViewById(R.id.mail_employee);
        password_employee = (EditText) findViewById(R.id.password_employee);
        username_employee = (EditText) findViewById(R.id.username_employee);
        role_employee = (EditText) findViewById(R.id.role_employee);
        btn_add_user = (Button) findViewById(R.id.adduser_employee);
        btn_list_employee = (Button) findViewById(R.id.listuser_employee);

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_user();
            }
        });

        btn_list_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(MainActivity_Administrator.this, List_employee.class);
                startActivity(mainIntent);
            }
        });
    }

    public void insert_user(){

        if(name_employee.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(lastname_employee.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(phone_employee.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(email_employee.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(password_employee.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(username_employee.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }
        if(role_employee.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Field not set", Toast.LENGTH_LONG).show();
            return;
        }

        url += "?nombre=" + name_employee.getText().toString() + "&apellido=" + lastname_employee.getText().toString() + "&telefono=" + phone_employee.getText().toString() + "&mail=" + email_employee.getText().toString() + "&password=" + password_employee.getText().toString() + "&username=" + username_employee.getText().toString() + "&role=" + role_employee.getText().toString();


        final JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonResponse = response.getString("Codigo");
                    jsonmessage = response.getString("Mensaje");


                    if((jsonResponse.compareTo("01") == 0)){
                        Toast.makeText(getApplicationContext(), jsonmessage, Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent().setClass(MainActivity_Administrator.this, List_employee.class);
                        startActivity(mainIntent);
                    }
                    if((jsonResponse.compareTo("04") == 0)){
                        Toast.makeText(getApplicationContext(), jsonmessage, Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent().setClass(MainActivity_Administrator.this, List_employee.class);
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
