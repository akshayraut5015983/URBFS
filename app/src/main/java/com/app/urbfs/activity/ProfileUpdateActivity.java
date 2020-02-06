package com.app.urbfs.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.urbfs.R;
import com.app.urbfs.config.Config;
import com.app.urbfs.config.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileUpdateActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    String URL = "";
    SessionManager session;
    SharedPreferences pref;
    String sesId = "", sesMobile = "", sesPass = "";
    EditText edLogId, edMName, edSpillby, edMobile, edPan, edEmail, edAddress, edGooglepay, edPhonPAy, edAdharCard;
    LinearLayout linearLayout;
    Button btnEdit, btnSave;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitty_profile_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edLogId = findViewById(R.id.edLogId);
        edMName = findViewById(R.id.edMName);
        edSpillby = findViewById(R.id.edSpillby);
        edMobile = findViewById(R.id.edMobile);
        edPan = findViewById(R.id.edPan);
        edEmail = findViewById(R.id.edEmail);
        edAddress = findViewById(R.id.edAddress);
        edGooglepay = findViewById(R.id.edGooglepay);
        edPhonPAy = findViewById(R.id.edPhonPAy);
        edAdharCard = findViewById(R.id.edAdharCard);
        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);
        session = new SessionManager(getApplicationContext());
        pref = getSharedPreferences(Config.PREF_NAME, Context.MODE_PRIVATE);
        if (pref.contains(Config.KEY_NAME)) {
            sesId = pref.getString(Config.KEY_NAME, "");
        }
        if (pref.contains(Config.KEY_MOBILE)) {
            sesMobile = pref.getString(Config.KEY_MOBILE, "");
        }
        if (pref.contains(Config.KEY_PASSWORD)) {
            sesPass = pref.getString(Config.KEY_PASSWORD, "");
        }
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* edLogId.setFocusableInTouchMode(true);
                edMName.setFocusableInTouchMode(true);
                edSpillby.setFocusableInTouchMode(true);
                edMobile.setFocusableInTouchMode(true);*/
                edPan.setFocusableInTouchMode(true);
                edEmail.setFocusableInTouchMode(true);
                edAddress.setFocusableInTouchMode(true);
                edGooglepay.setFocusableInTouchMode(true);
                edPhonPAy.setFocusableInTouchMode(true);
                edAdharCard.setFocusableInTouchMode(true);



                edPan.setText("");
                edEmail.setText("");
                edAddress.setText("");
                edGooglepay.setText("");
                edPhonPAy.setText("");
                edAdharCard.setText("");

                btnEdit.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ProfileUpdateActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
                builder2.setTitle("You want to update");
                builder2.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder2.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProfileUpdateActivity.this, "Saving", Toast.LENGTH_SHORT).show();
                    }
                });
                builder2.setCancelable(false);
                builder2.show();
            }
        });

        pDialog = new ProgressDialog(this);
        // http://urbsfhelp.live/API/APIURL.aspx?msg=Profile%20sai&mobile=123456789
        URL = Config.URL + "API/APIURL.aspx?msg=Profile%20" + sesId + "&Mobile=" + sesMobile;
        loadSpinnerData(URL);
        pDialog.setMessage("Please wait...");
        pDialog.show();
    }

    private void loadSpinnerData(final String url) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pDialog.dismiss();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ProfileUpdateActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
                builder2.setTitle("Profile details not loaded");
                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder2.setPositiveButton("Refresh to load", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        loadSpinnerData(URL);
                    }
                });
                builder2.setCancelable(false);
                builder2.show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileUpdateActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseData(JSONArray array) {
        pDialog.dismiss();

        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);
                edLogId.setText("Login ID-    " + String.valueOf(json.getString("loginid")));
                edMName.setText("Member name- " + String.valueOf(json.getString("membername")));
                edSpillby.setText("Spill by-    " + String.valueOf(json.getString("spillby")));
                edMobile.setText("Mobile-      " + String.valueOf(json.getString("mobile")));
                edPan.setText("PAN no-      " + String.valueOf(json.getString("panno")));
                edEmail.setText("Email-       " + String.valueOf(json.getString("email")));
                edAddress.setText("Address-     " + String.valueOf(json.getString("address")));
                edGooglepay.setText("Google Pay-  " + String.valueOf(json.getString("googlepay")));
                edPhonPAy.setText("Phon pay-    " + String.valueOf(json.getString("phonepay")));
                edAdharCard.setText("Aadhar no-   " + String.valueOf(json.getString("aadharno")));


                Log.d("pinamount", json.getString("membername"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
