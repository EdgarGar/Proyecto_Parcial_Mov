package com.max.proyecto_parcial2.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.max.proyecto_parcial2.Cardview_products;
import com.max.proyecto_parcial2.MainActivity_Employee;
import com.max.proyecto_parcial2.Product;
import com.max.proyecto_parcial2.R;
import com.max.proyecto_parcial2.parserJSON_products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AudioFragment extends Fragment{

    //trate de ordenar los productos por categoria, pero se crasheo la app.
    /*private final String TAG = "ListProducts";
    private ArrayList<Product> listProduct = new ArrayList<>();
    private RecyclerView lv;*/

    public AudioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.fragment_audio);

        /*lv = (RecyclerView) getActivity().findViewById(R.id.audio_list);
        lv.setLayoutManager(new LinearLayoutManager(getContext()));

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/producto.r.php";

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //listProduct = parserJSON_products.parseaArreglo(response);

                        JSONObject obj=null;
                        Product producto = null;
                        listProduct.clear();

                        try {
                            for(int i = 0;i<response.length();i++) {

                                obj = response.getJSONObject(i);
                                producto = new Product();
                                try{
                                if(producto.getCategory().compareTo("1") == 0) {
                                    producto.setName(obj.getString("nombre"));
                                    producto.setid(obj.getString("id"));
                                    producto.setImage(obj.getString("imagen"));
                                    producto.setPrice(obj.getString("precio"));
                                    producto.setStock(obj.getString("stock"));
                                    producto.setCategory(obj.getString("Categoria"));
                                    listProduct.add(producto);
                                }
                                else
                                {

                                }
                                }catch (Exception e2)
                                {
                                    e2.printStackTrace();
                                }
                            }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                        Cardview_products arrayAdapter = new Cardview_products(getContext(), listProduct);
                        lv.setAdapter(arrayAdapter);


                        arrayAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error en: " + error.getMessage());
            }
        });
        Volley.newRequestQueue(getContext()).add(product_json_array);*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio, container, false);
    }
}
