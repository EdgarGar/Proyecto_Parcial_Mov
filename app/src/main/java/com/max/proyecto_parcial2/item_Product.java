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

public class item_Product extends AppCompatActivity {
    private TextView t_name, t_price, t_subtotal, t_quantity, t_image;
    private EditText p_id, p_name, p_image, p_stock, p_price, p_category;
    private String jsonSubtotal, jsonQuantity;
    private String value = "", orderID = "";
    private Button btn_increment, btn_decrement, btn_send;
    private int counter = 0;
    private ImageView imageView;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_product);
        queue = Volley.newRequestQueue(getApplicationContext());

        Bundle myIntent = getIntent().getExtras();

        if(myIntent != null) {
            value = myIntent.getString("idProduct");
        }

        t_name = (TextView) findViewById(R.id.ProductName);
        t_price = (TextView) findViewById(R.id.ProductPrice);
        t_quantity = (TextView) findViewById(R.id.ProductQuantity);
        t_subtotal = (TextView) findViewById(R.id.Subtotal);
        t_image = (TextView) findViewById(R.id.Image_url);
        imageView = (ImageView) findViewById(R.id.imageViewURL);

        btn_increment = (Button) findViewById(R.id.addquantity);
        btn_decrement = (Button) findViewById(R.id.removequantity);
        btn_send = (Button) findViewById(R.id.additem_tocart);


        btn_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });

        btn_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });

        // if the user is a client, than the product should be just added to the cart
        if (UserVariables.getInstance().isClientUser()) {
            btn_send.setText(R.string.product_btn_client);

            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart();
                }
            });
        } else {
            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createOrder();
                }
            });
        }

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/producto.r.php?id=" + value;

        JsonArrayRequest jsonrequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject product = (JSONObject) response.get(0);

                    t_name.setText(R.string.product + ":" + product.getString("nombre"));
                    t_price.setText(R.string.unit_price + ": "  + product.getString("precio"));
                    t_image.setText(product.getString("imagen"));

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

    public void increment(){
        counter++;
        String[] separated = t_price.getText().toString().split(":");
        t_subtotal.setText("Subtotal: " + (Integer.parseInt(separated[1])) * counter);
        t_quantity.setText("Quantity: " + (String.valueOf(counter)));
    }

    public void decrement(){
        if(counter >= 1)
        {
            counter--;
            String[] separated = t_price.getText().toString().split(":");
            t_subtotal.setText("Subtotal: " + (Integer.parseInt(separated[1])) * counter);
            t_quantity.setText("Quantity: " + (String.valueOf(counter)));
        }
    }

    public void createOrder() {
        //--crear pedido con servicio pedido.c.php
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/pedidos.c.php" +
                "?ide=" + UserVariables.getInstance().getEmployeeID() +
                "&idc=" + UserVariables.getInstance().getClientID() +
                "&status=1";
        Log.d("Request 1", url);

        // creates a new order if one doesnt exist yet
        if (UserVariables.getInstance().getOrderID() == null) {
            JsonObjectRequest creaPedido = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("Code").equals("01")) {
                            orderID = response.getString("IDPedido");
                            Log.d("Good Response 1", response.toString() + " " + response.getString("Code"));
                            UserVariables.getInstance().setOrderID(orderID);
                            buyProduct();
                            Intent orderIntent = new Intent().setClass(item_Product.this, Order.class);
                            Bundle id = new Bundle();
                            id.putString("idOrder", UserVariables.getInstance().getOrderID());
                            orderIntent.putExtras(id);
                            startActivity(orderIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Could not create the order. Check internet connection or contact admin.", Toast.LENGTH_LONG).show();
                            //orderID = null;
                            //UserVariables.getInstance().setOrderID(orderID);
                            Log.d("Bad Response 1", response.toString() + " " + response.getString("Code"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(creaPedido);
        } else {
            buyProduct();
        }

        //////////////////////////////TO DO/////////////////////////////////
        //--crear pedido con servicio pedido.c.php
        //--crear pedido_producto con counter como cantidad de producto,
        //// value como id del producto, y se obtiene el total con
        //// Integer.parseInt(separated[1])) * counter, y de la misma forma,
        //// se obtienen los calculos para el 5%.
        //--Administrador pueda ver todos los pedidos.
        //--Crear pantalla de carrito donde se vean todos los productos del pedido
        //--Terminar codigo cuando administrador quiera ver datos de un Empleado.
        //--Mejorar Servicios php
        ////////////////////////////////////////////////////////////////////
    }

    public void buyProduct() {
        //--crear pedido_producto con counter como cantidad de producto,
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/productos_pedidos.c.php" +
                "?idpr=" + UserVariables.getInstance().getOrderID() +
                "&idpe=" + value +
                "&cant=" + counter +
                "&ide=" + UserVariables.getInstance().getEmployeeID();
        Log.d("Request 2", url);

        JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("Code").equals("01")) {
                        Toast.makeText(getApplicationContext(), "Item bought" , Toast.LENGTH_LONG).show();
                        Log.d("Good Response 2", response.toString() + " " + response.getString("Code"));
                    } else {
                        Log.d("Bad Response 2", response.toString() + " " + response.getString("Code"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonrequest);
    }

    public void addToCart() {
        if (counter > 0) {
            UserVariables.cart.add(new UserVariables.CartItem(UserVariables.getInstance().tempProduct, counter));
            Toast.makeText(getApplicationContext(), "Item(s) added to cart", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Can't buy 0 products", Toast.LENGTH_LONG).show();
        }
    }

        /////////////////////////////BONUS//////////////////////////////////
        //--Dividir productos por categorias en los fragmentos.
        //--Administrador pueda modificar Empleado.
        //--Encriptar carpeta de proyecto.
        //--Agregar funcion getParams() al login.
        //--Administrador pueda ver clientes, y pueda añadir/modificar clientes.
        //--Administrador pueda ver que Empleado realizo que pedido.
        //--Añadir fotos a empleados, y clientes.
        ////////////////////////////////////////////////////////////////////
}
