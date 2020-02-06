package com.app.urbfs.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.urbfs.R;
import com.app.urbfs.adapter.TestAdapter;
import com.app.urbfs.model.RequestPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsAndUpdateActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressDialog loading;
    private List<RequestPojo> listSuperHeroes;
    private RecyclerView.Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsupdate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listSuperHeroes = new ArrayList<>();
        recyclerView =  findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getData();
      }
    private void getData() {

        String MY_URL = "http://dblcapp.com/api/customer.aspx?msg=GetBaner";
        loading = ProgressDialog.show(NewsAndUpdateActivity.this, "Loading Data", "Please Wait...", false, false);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(MY_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response", String.valueOf(response));
                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(NewsAndUpdateActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseData(JSONArray array) {
        try {

            //  JSONArray array = new JSONArray(aarray);
            for (int i = 0; i < array.length(); i++) {
                RequestPojo superHero = new RequestPojo();
                JSONObject json = null;
                try {

                    json = array.getJSONObject(i);
                    superHero.setBanerTitle(json.getString("BanerTitle"));
                    superHero.setBanerImage(json.getString("BanerImage"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listSuperHeroes.add(superHero);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new TestAdapter(listSuperHeroes, NewsAndUpdateActivity.this);
        Log.d("tag", String.valueOf(adapter.getItemCount()));
        recyclerView.setAdapter(adapter);
        loading.dismiss();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


