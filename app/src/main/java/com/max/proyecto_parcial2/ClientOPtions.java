package com.max.proyecto_parcial2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientOPtions extends AppCompatActivity {
    private TextView id, nombre, apellido, mail, direccion, imagen;
    private String jsonResponse, jsonmessage;
    private String value = "";
    private Button btn_delete;
    private Button btn_select;
    private ImageView imageView;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_options);
        queue = Volley.newRequestQueue(getApplicationContext());

        nombre = (TextView) findViewById(R.id.ClientName_administrator);
        id = (TextView) findViewById(R.id.IdClient_administrator);
        imagen = (TextView) findViewById(R.id.Image_url_client_administrator);
        imageView = (ImageView) findViewById(R.id.imageViewURL_client_administrator);
        apellido = (TextView) findViewById(R.id.ClientApellido_administrator);
        mail = (TextView) findViewById(R.id.MailClient_administrator);
        direccion = (TextView) findViewById(R.id.AddressClient_administrator);

        btn_delete = (Button) findViewById(R.id.btn_delete_client);
        btn_select = (Button) findViewById(R.id.btn_select_client);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent().setClass(ClientOPtions.this, MainActivity_Employee.class);
                startActivity(listIntent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_user();
                Intent listIntent = new Intent().setClass(ClientOPtions.this, ChooseClient.class);
                startActivity(listIntent);
            }
        });

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/cliente.r.php?id=" + value;

        JsonArrayRequest jsonrequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject product = (JSONObject) response.get(0);

                    nombre.setText("Name: " + product.getString("nombre"));
                    apellido.setText("Last name: "  + product.getString("apellido"));
                    mail.setText("Mail: " +product.getString("mail"));
                    id.setText("ID: " + product.getString("id"));
                    direccion.setText("Address: " + product.getString("direccion"));
                    imagen.setText(product.getString("imagen"));

                    try {
                        if(imagen.getText().equals("")) {
                            ImageDownloader task = new ImageDownloader();
                            Bitmap myImage = task.execute(imagen.getText().toString()).get();
                            imageView.setImageBitmap(myImage);
                        }
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
        queue.add(jsonrequest);

    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{
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

    void delete_user(){
        String value = UserVariables.getInstance().getClientID();

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/cliente.d.php?id=" + value;

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
}

