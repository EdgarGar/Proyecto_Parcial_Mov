package com.max.proyecto_parcial2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    private ImageView imageView;
    private Integer value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        int waitTime = 2000;
        Random rand = new Random();
        value = rand.nextInt(3) + 1;


        imageView = (ImageView) findViewById(R.id.splash_image);

        final String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Imagenes/" + value + ".jpg";

        try {
            Splash.ImageDownloader task = new Splash.ImageDownloader();
            Bitmap myImage = task.execute(url).get();
            imageView.setImageBitmap(myImage);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e("ERROR", e.getMessage());
        }


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent mainIntent = new Intent().setClass(Splash.this, LoginActivity.class);
                startActivity(mainIntent);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, waitTime);


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
