package com.app.urbfs.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.app.urbfs.model.ForPin;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NewRegistrationUser extends AppCompatActivity {

    private TextView tvPinname, tvPinAmt, tvSponName;
    private Button register, btnGetSponId;
    Spinner spinSpin;
    private ProgressDialog pDialog;
    ArrayList<ForPin> countryName;
    ArrayList<String> pin;
    private String strName = "", strLname = "", strEmail = "", strMob = "", strAdd = "", strAdhar = "", strPanCard = "", strPPay = "", strGPAy = "", strPass = "", strConPAass = "", strSponID = "";
    EditText edFname, edLname, edMobile, edEmail, edAddress, edSpinserId, edAdharCard, edPanCard, edPhonPAy, edGooglepay, edPAsss, edConPass;
    String URL = "";
    SessionManager session;
    SharedPreferences pref;
    String sesId = "", sesMobile = "", sesPass = "";
    String pinName = "", pinAmt = "", pinNo = "";
    private LinearLayout mainLayout;
    String mainUrl = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newregister_activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        mainLayout = findViewById(R.id.mainReg);
        tvPinAmt = findViewById(R.id.tvPinAmt);
        tvPinname = findViewById(R.id.tvPinname);
        edFname = findViewById(R.id.edFname);
        edLname = findViewById(R.id.edLname);
        edMobile = findViewById(R.id.edMobile);
        edEmail = findViewById(R.id.edEmail);
        edAddress = findViewById(R.id.edAddress);
        edAdharCard = findViewById(R.id.edAdharCard);
        edPanCard = findViewById(R.id.edPanCard);
        edPhonPAy = findViewById(R.id.edPhonPAy);
        edGooglepay = findViewById(R.id.edGooglepay);
        edPAsss = findViewById(R.id.edPAsss);
        edConPass = findViewById(R.id.edConPass);
        edSpinserId = findViewById(R.id.edSpinserId);
        btnGetSponId = findViewById(R.id.btnGetSponId);
        tvSponName = findViewById(R.id.tvSponName);
        spinSpin = (Spinner) findViewById(R.id.spinSpin);
        register = (Button) findViewById(R.id.btnSignUp);

        countryName = new ArrayList<>();
        pin = new ArrayList<>();
        URL = Config.URL + "API/APIURL.aspx?msg=pin%20" + sesId + "&Mobile=" + sesMobile;
        loadSpinnerData(URL);
        pDialog = new ProgressDialog(this);

        spinSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   String strRefId = spinSpin.getItemAtPosition(spinSpin.getSelectedItemPosition()).toString();
                pinAmt = countryName.get(i).getPinamount();
                pinName = countryName.get(i).getPinname();
                pinNo = countryName.get(i).getPinno();
                tvPinAmt.setText("Pin Amount- " + pinAmt);
                tvPinname.setText("Pin Name- " + pinName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        btnGetSponId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edSpinserId.getText().toString().equals("")) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the id", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo nf = cn.getActiveNetworkInfo();
                    if (nf != null && nf.isConnected() == true) {
                        sesId = edSpinserId.getText().toString();
                        // http://urbsfhelp.live//API/APIURL.aspx?msg=BindSponcerName%20sai&Mobile=123456789
                        String url = Config.URL + "/API/APIURL.aspx?msg=BindSponcerName%20" + sesId + "&Mobile=" + sesMobile;
                        Log.d("Register Url", url);
                        DownloadWebPageTaskSpId task1 = new DownloadWebPageTaskSpId();
                        task1.execute(new String[]{String.valueOf(url)});
                        pDialog = new ProgressDialog(NewRegistrationUser.this);
                        pDialog.setMessage("Please wait...");
                        pDialog.setCancelable(false);
                        pDialog.show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Network Connection Not Available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nf = cn.getActiveNetworkInfo();

                strName = edFname.getText().toString();
                strLname = edLname.getText().toString();
                strEmail = edEmail.getText().toString();
                strMob = edMobile.getText().toString();
                strAdd = edAddress.getText().toString();
                strAdhar = edAdharCard.getText().toString();
                strPanCard = edPanCard.getText().toString();
                strPPay = edPhonPAy.getText().toString();
                strGPAy = edGooglepay.getText().toString();
                strPass = edPAsss.getText().toString();
                strConPAass = edConPass.getText().toString();
                strName = edFname.getText().toString();
                strName = edFname.getText().toString();
                strName = edFname.getText().toString();
                strName = edFname.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (strName.equals("") || strLname.equals("")) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the name", Toast.LENGTH_SHORT).show();
                } else if (strAdd.equals("")) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the address", Toast.LENGTH_SHORT).show();
                } else if (strMob.equals("") || strMob.length() != 10) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the valid mobile", Toast.LENGTH_SHORT).show();
                } else if (strEmail.equals("") || !strEmail.matches(emailPattern)) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the valid email", Toast.LENGTH_SHORT).show();
                } else if (strAdhar.equals("")) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the addhar card", Toast.LENGTH_SHORT).show();
                } else if (strPanCard.equals("")) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the pan card", Toast.LENGTH_SHORT).show();
                } else if (strPPay.equals("")) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the phon pay", Toast.LENGTH_SHORT).show();
                } else if (strGPAy.equals("")) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the google pay", Toast.LENGTH_SHORT).show();
                } else if (strPass.equals("")) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the password", Toast.LENGTH_SHORT).show();
                } else if (strConPAass.equals("")) {
                    Toast.makeText(NewRegistrationUser.this, "Enter the confirm password", Toast.LENGTH_SHORT).show();
                } else if (!strPass.equals(strConPAass)) {
                    Toast.makeText(NewRegistrationUser.this, "Password not match", Toast.LENGTH_SHORT).show();
                } else {
                    if (nf != null && nf.isConnected() == true) {

                        //http://urbsfhelp.live/API/APIURL.aspx?msg=sai%20leftside%20test%20test@gmailcom%20123%20232%209890989088%20abc433%20232%20545%2034%20257TKW0244831&Mobile=123456789
                        mainUrl = Config.URL + "/API/APIURL.aspx?msg=" + sesId + "%20leftside%20" + strName + strLname + "%20" + strEmail + "%20" + strConPAass + "%20" + strPPay + "%20" + strMob + "%20" + strAdd + "%20" + strAdhar + "%20" + strPanCard + "%20" + strGPAy + "%20" + pinNo + "&Mobile=" + sesMobile;
                        Log.d("Register Url", mainUrl);
                        // http://urbsfhelp.live/API/APIURL.aspx?msg=chkpanno%2012345&mobile=123456789
                        String url = Config.URL + "API/APIURL.aspx?msg=chkpanno%20" + strPanCard + "&mobile=" + sesMobile;
                        DownloadPan task1 = new DownloadPan();
                        task1.execute(new String[]{String.valueOf(url)});
                        Log.d("pan Url", url);
                        pDialog = new ProgressDialog(NewRegistrationUser.this);
                        pDialog.setMessage("Please wait...");
                        pDialog.setCancelable(false);
                        pDialog.show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Network Connection Not Available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void loadSpinnerData(final String url) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDialog.setMessage("Please wait Sponcer id Loading...");
                pDialog.show();
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pDialog.dismiss();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(NewRegistrationUser.this, android.R.style.Theme_DeviceDefault_Dialog);
                builder2.setTitle("Sponsor id not load");
                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
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

        RequestQueue requestQueue = Volley.newRequestQueue(NewRegistrationUser.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseData(JSONArray array) {
        pDialog.dismiss();
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;
            ForPin forPin = new ForPin();
            try {
                json = array.getJSONObject(i);
                //  String strPaymentPin = json.getString("pinname") + " | " + json.getString("pinno") + " | " + json.getString("pinamount");
                // countryName.add(strPaymentPin);
                forPin.setPinamount(json.getString("pinamount"));
                forPin.setPinname(json.getString("pinname"));
                forPin.setPinno(json.getString("pinno"));
                countryName.add(forPin);
                pin.add(json.getString("pinno"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (pin.size() == 0) {
            btnGetSponId.setVisibility(View.GONE);
            Toast.makeText(this, "ID not found", Toast.LENGTH_SHORT).show();
        }
        spinSpin.setAdapter(new ArrayAdapter<String>(NewRegistrationUser.this, android.R.layout.simple_spinner_dropdown_item, pin));
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

    public class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            String s = Html.fromHtml(result).toString();

            if (pDialog.isShowing())
                pDialog.dismiss();

            AlertDialog.Builder builder2 = new AlertDialog.Builder(NewRegistrationUser.this);
            builder2.setTitle("Registration");
            builder2.setMessage(String.valueOf(s).toString().trim());
            builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            builder2.show();
        }
    }

    public class DownloadWebPageTaskSpId extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            String line1 = Html.fromHtml(result).toString();
            String s = line1.replace("\"", "");
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (s.contains("invalid")) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(NewRegistrationUser.this);
                builder2.setTitle("Id not Valid");

                builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        edSpinserId.setText("");
                    }
                });
                builder2.setCancelable(false);
                builder2.show();
            } else {
                tvSponName.setText(s);
                mainLayout.setVisibility(View.VISIBLE);
                btnGetSponId.setVisibility(View.GONE);
                tvSponName.setVisibility(View.VISIBLE);
                edSpinserId.setFocusable(false);
            }
        }
    }

    public class DownloadPan extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            String s = Html.fromHtml(result).toString();
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (s.contains("Already Used")) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(NewRegistrationUser.this);
                builder2.setTitle("PAN number already ased");
                builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        edPanCard.setText("");
                    }
                });
                builder2.setCancelable(false);
                builder2.show();
            } else {
                DownloadWebPageTask task1 = new DownloadWebPageTask();
                task1.execute(new String[]{String.valueOf(mainUrl)});
                pDialog = new ProgressDialog(NewRegistrationUser.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();

            }
        }
    }


}
