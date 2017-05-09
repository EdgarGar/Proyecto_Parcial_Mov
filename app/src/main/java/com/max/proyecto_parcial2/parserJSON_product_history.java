package com.max.proyecto_parcial2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by artalves07 on 9/05/17.
 */

public class parserJSON_product_history {
    public static ArrayList<ProductHistoryItem> _array_products = new ArrayList<>();
    public static ProductHistoryItem paseaObjeto(JSONObject obj) {

        try {
            ProductHistoryItem product = new ProductHistoryItem();

            product.setName(obj.getString("0"));
            product.setQuantity(Integer.parseInt(obj.getString("2")));
            product.setPrice(Integer.parseInt(obj.getString("cantidad")));

            String date[] = obj.getString("1").split(" ")[0].split("-");
            product.setDay(date[2]);
            product.setMonth(date[1]);
            product.setYear(date[0]);

            return product;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static ArrayList<ProductHistoryItem> parseaArreglo(JSONArray arr) {

        JSONObject obj=null;
        ProductHistoryItem producto = null;
        _array_products.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                producto = new ProductHistoryItem();

                producto.setName(obj.getString("0"));
                producto.setQuantity(Integer.parseInt(obj.getString("2")));
                producto.setPrice(Integer.parseInt(obj.getString("cantidad")));
                String date[] = obj.getString("1").split(" ")[0].split("-");

                producto.setDay(date[2]);
                producto.setMonth(date[1]);
                producto.setYear(date[0]);

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
