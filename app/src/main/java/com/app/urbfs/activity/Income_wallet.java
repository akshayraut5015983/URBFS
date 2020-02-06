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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.urbfs.R;
import com.app.urbfs.config.Config;
import com.app.urbfs.config.SessionManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Income_wallet extends AppCompatActivity {
    String URL = Config.URL + "API/APIURL.aspx?msg=pin%20sai&Mobile=123456789";
    SessionManager session;
    SharedPreferences pref;
    String sesId = "", sesMobile = "", sesPass = "";
    private ProgressDialog pDialog;
    TextView tvAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_wallet);
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
        tvAmt = findViewById(R.id.tvAmt);
        pDialog = new ProgressDialog(this);
        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {

            // http://urbsfhelp.live/API/APIURL.aspx?msg=Ewallet%20sai&mobile=123456789
            String url = Config.URL + "API/APIURL.aspx?msg=Ewallet%20" + sesId + "&mobile=" + sesMobile;
            Log.d("Register Url", url);
            DownloadWalletAmount task1 = new DownloadWalletAmount();
            task1.execute(new String[]{String.valueOf(url)});
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        } else {
            Toast.makeText(getApplicationContext(), "Network Connection Not Available", Toast.LENGTH_LONG).show();
        }
    }

    public class DownloadWalletAmount extends AsyncTask<String, Void, String> {

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
            String str = "Your wallet amount is-  " + s;
            tvAmt.setText(str);
            AlertDialog.Builder builder2 = new AlertDialog.Builder(Income_wallet.this);
            builder2.setTitle(str);

            builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            builder2.setCancelable(false);
            builder2.show();

        }
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
