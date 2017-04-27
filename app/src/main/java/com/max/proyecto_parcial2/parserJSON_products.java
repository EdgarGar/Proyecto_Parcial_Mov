package com.max.proyecto_parcial2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class parserJSON_products {

    public static ArrayList<Product> _array_products = new ArrayList<>();
    public static Product paseaObjeto(JSONObject obj) {

        try {
            Product product = new Product();

            product.setid(obj.getString("id"));
            product.setName(obj.getString("nombre"));
            product.setImage(obj.getString("imagen"));
            product.setPrice(obj.getString("precio"));
            product.setStock(obj.getString("stock"));
            product.setCategory(obj.getString("Categoria"));
            return product;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Product> parseaArreglo(JSONArray arr) {

        JSONObject obj=null;
        Product producto = null;
        _array_products.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                producto = new Product();

                producto.setName(obj.getString("nombre"));
                producto.setid(obj.getString("id"));
                producto.setImage(obj.getString("imagen"));
                producto.setPrice(obj.getString("precio"));
                producto.setStock(obj.getString("stock"));
                producto.setCategory(obj.getString("Categoria"));

                _array_products.add(producto);
            }
            return _array_products;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> stringArray(JSONArray arr){

        JSONObject obj=null;
        ArrayList<String> res = new ArrayList<>();

        try {
            for(int i = 0;i<arr.length();i++) {
                obj = arr.getJSONObject(i);

                res.add(obj.getString("nombre"));
            }
            Log.i("Debug note: ", "returned " + res.toString());
            return res;

        } catch (JSONException e1) {
            Log.e("Error: ", "returned null");
            e1.printStackTrace();
            return null;
        }

    }
}

