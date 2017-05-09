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

public class ItemProductAdministrator extends AppCompatActivity {
    private TextView t_name, t_price, t_image, t_id, t_stock, t_category;
    private String jsonResponse, jsonmessage;
    private String value = "";
    private Button btn_delete;
    private ImageView imageView;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_product_administrator);
        queue = Volley.newRequestQueue(getApplicationContext());

        Bundle myIntent = getIntent().getExtras();

        if(myIntent != null) {
            value = myIntent.getString("idProduct");
        }

        t_name = (TextView) findViewById(R.id.ProductName_administrator);
        t_price = (TextView) findViewById(R.id.ProductPrice_administrator);
        t_image = (TextView) findViewById(R.id.Image_url_administrator);
        imageView = (ImageView) findViewById(R.id.imageViewURL_administrator);
        t_id = (TextView) findViewById(R.id.IdProduct_administrator);
        t_stock = (TextView) findViewById(R.id.Stock_administrator);
        t_category = (TextView) findViewById(R.id.Category_administrator);


        btn_delete = (Button) findViewById(R.id.btn_delete_product);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_product();
                Intent mainIntent = new Intent().setClass(ItemProductAdministrator.this, OptionsAdministrator.class);
                startActivity(mainIntent);
            }
        });

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/producto.r.php?id=" + value;

        JsonArrayRequest jsonrequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject product = (JSONObject) response.get(0);

                    t_name.setText(R.string.product_name + ": " + product.getString("nombre"));
                    t_price.setText(R.string.unit_price + ": "  + product.getString("precio"));
                    t_image.setText(product.getString("imagen"));
                    t_id.setText("ID: " + product.getString("id"));
                    t_stock.setText(R.string.stockProduct + ": " + product.getString("stock"));
                    t_category.setText(R.string.category_product + ": " + product.getString("Categoria"));

                    try {
                        ImageDownloader task = new ImageDownloader();
                        Bitmap myImage = task.execute(t_image.getText().toString()).get();
                        imageView.setImageBitmap(myImage);
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

    void delete_product(){
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/producto.d.php?id=" + value;

        final JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonResponse = response.getString("Code");
                    jsonmessage = response.getString("Message");


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

