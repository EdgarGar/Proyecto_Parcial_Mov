package com.max.proyecto_parcial2;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
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

public class item_Employee extends AppCompatActivity {

    private String value = "";

    private ImageView image;
    private TextView name;
    private TextView username;
    private TextView email;
    private TextView mobile;
    private TextView role;
    private TextView payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                    /*try {
                        item_Product.ImageDownloader task = new item_Product.ImageDownloader();
                        Bitmap myImage = task.execute(t_image.getText().toString()).get();
                        imageView.setImageBitmap(myImage);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }*/

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

    ///////////////////BONUS/////////////////////
    //--Boton que lleve a otra actividad para que se modifiquen datos.
    ////////////////////////////////////////////

}

