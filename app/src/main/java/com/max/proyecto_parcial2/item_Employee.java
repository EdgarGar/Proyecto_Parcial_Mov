package com.max.proyecto_parcial2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class item_Employee extends AppCompatActivity {

    private String value = "";

    private ImageView image;
    private TextView name;
    private TextView username;
    private TextView email;
    private TextView mobile;
    private TextView role;
    private TextView payment;
    private TextView t_image;

    String jsonResponse, jsonmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button delete;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_employee);

        Bundle myIntent = getIntent().getExtras();

        if(myIntent != null) {value = myIntent.getString("idEmployee");}

        /////////////////TO DO HERE//////////////////
        //--Añadir textviews suficientes para cada campo del Empleado (nombre, apellido, usuario, rol, password, etc).
        //--Que cada textview despliegue informacion de acuerdo al campo.
        //// (Se puede obtener la informacion del Empleado directamente desde el servicio empleado.r.php?id=) Vease como se hace en item_Product.java
        //--Desplegar foto de Empleado. Vease como se hizo en item_Product.java con Bitmap.
        //--Boton de regresar a Pantalla anterior.
        ////////////////////////////////////////////

        // Find the View that shows the complete name (name and last name)
        name = (TextView) findViewById(R.id.name);
        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        mobile = (TextView) findViewById(R.id.mobile);
        role = (TextView) findViewById(R.id.role);
        payment = (TextView) findViewById(R.id.payment);
        image = (ImageView) findViewById(R.id.profile);
        t_image = (TextView) findViewById(R.id.Image_url);

        delete = (Button) findViewById(R.id.btn_delete);

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/empleado.r.php?id_empleado=" + value;

        JsonArrayRequest jsonrequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject employee = (JSONObject) response.get(0);

                    name.setText(employee.getString("nombre") + " " + employee.getString("apellido"));
                    username.setText(employee.getString("username"));
                    email.setText(employee.getString("mail"));
                    mobile.setText(employee.getString("telefono"));
                    role.setText(employee.getString("rol"));
                    payment.setText(employee.getString("sueldo"));
                    t_image.setText(employee.getString("imagen"));

                    try {
                        item_Employee.ImageDownloader task = new item_Employee.ImageDownloader();
                        Bitmap myImage = task.execute(t_image.getText().toString()).get();
                        image.setImageBitmap(myImage);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_user();
            }
        });

    }

    ///////////////////BONUS/////////////////////
    //--Boton que lleve a otra actividad para que se modifiquen datos.
    ////////////////////////////////////////////
    void delete_user(){

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/empleado.d.php?id_empleado=" + value;

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

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls){
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream= connection.getInputStream();

                Bitmap myBitmap =  BitmapFactory.decodeStream(inputStream);

                return myBitmap;
            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }
}

