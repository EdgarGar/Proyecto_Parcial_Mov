package com.max.proyecto_parcial2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.max.proyecto_parcial2.Fragments.AudioFragment;
import com.max.proyecto_parcial2.Fragments.InstrumentFragment;
import com.max.proyecto_parcial2.Fragments.TVFragment;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Employee extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private final String TAG = "ListProducts";
    private ArrayList<Product> listProduct = new ArrayList<>();
    private RecyclerView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_employee);
        UserVariables.getInstance().setClientID("1");
        UserVariables.getInstance().setOrderID(null);

        lv = (RecyclerView) findViewById(R.id.car_list);
        lv.setLayoutManager(new LinearLayoutManager(this));

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/producto.r.php";

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listProduct = parserJSON_products.parseaArreglo(response);

                        Cardview_products arrayAdapter = new Cardview_products(getApplicationContext(), listProduct);
                        lv.setAdapter(arrayAdapter);


                        arrayAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error en: " + error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(product_json_array);
    }

//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new AudioFragment(), "AUDIO");
//        adapter.addFragment(new TVFragment(), "TV");
//        adapter.addFragment(new InstrumentFragment(), "INSTRUMENT");
//        viewPager.setAdapter(adapter);
//    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
