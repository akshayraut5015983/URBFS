package com.app.urbfs.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

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
import com.app.urbfs.adapter.HistoryAdapter;
import com.app.urbfs.config.Config;
import com.app.urbfs.config.SessionManager;
import com.app.urbfs.model.HistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Income_report extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog loading;
    private List<HistoryModel> listSuperHeroes;
    private RecyclerView.Adapter adapter;
    SessionManager session;
    SharedPreferences pref;
    String loginid = "", mobilenumber = "", passwords = "", strLevel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        pref = getSharedPreferences(Config.PREF_NAME, Context.MODE_PRIVATE);
        if (pref.contains(Config.KEY_NAME)) {
            loginid = pref.getString(Config.KEY_NAME, "");
        }
        if (pref.contains(Config.KEY_MOBILE)) {
            mobilenumber = pref.getString(Config.KEY_MOBILE, "");
        }
        if (pref.contains(Config.KEY_PASSWORD)) {
            passwords = pref.getString(Config.KEY_PASSWORD, "");
        }

        listSuperHeroes = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        loading = ProgressDialog.show(this, "Loading Data", "Please Wait...", false, false);
        getData();
        adapter = new HistoryAdapter(listSuperHeroes, this);
        Log.d("tag", String.valueOf(adapter.getItemCount()));
        recyclerView.setAdapter(adapter);
    }

    private void getData() {

        //http://urbsfhelp.live/API/APIURL.aspx?msg=Income%20sai&mobile=123456789
        String MY_URL = Config.URL + "API/APIURL.aspx?msg=Income%20" + loginid + "&mobile=" + mobilenumber;


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(MY_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response history", String.valueOf(response));
                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.d("incomreport", error.getMessage());
                        error.getMessage();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(Income_report.this);
        requestQueue.add(jsonArrayRequest);

    }

    private void parseData(JSONArray array) {
        try {

            //  JSONArray array = new JSONArray(aarray);
            for (int i = 0; i < array.length(); i++) {
                HistoryModel superHero = new HistoryModel();
                JSONObject json = null;
                try {
                    json = array.getJSONObject(i);
                    superHero.setPayname(json.getString("payname"));
                    superHero.setPayoutdate(json.getString("payoutdate"));
                    superHero.setAmount(json.getString("amount"));
                    superHero.setTds(json.getString("tds"));
                    superHero.setAdmin(json.getString("admin"));
                    superHero.setNetpay(json.getString("netpay"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listSuperHeroes.add(superHero);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("histcout", String.valueOf(adapter.getItemCount()));
     //   Toast.makeText(this, String.valueOf(adapter.getItemCount()), Toast.LENGTH_SHORT).show();
        if (adapter.getItemCount() == 0) {
            Toast.makeText(this, "Data not available", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
        loading.dismiss();
    }

    @Override
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
