package com.app.urbfs.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.urbfs.R;
import com.app.urbfs.config.Config;
import com.app.urbfs.config.SessionManager;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Frontview extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout sliderLayout;
    HashMap<String, Integer> Hash_file_maps;

    TextView appname;

    ProgressDialog progressBar;
    SessionManager session;
    SharedPreferences pref;
    String loginid = "", mobilenumber = "", passwords = "", s;
    Context context;
    ImageView homefooter, trend;
    TextView hometext;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontview);
//
        appname = (TextView) findViewById(R.id.txtappname);
        appname.setText(R.string.app_name);

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
        Log.d("id", loginid);
        Log.d("mob", mobilenumber);
        Log.d("pass", passwords);
        homefooter = (ImageView) findViewById(R.id.home_footer);
        hometext = (TextView) findViewById(R.id.hometextfooter);
        hometext.setVisibility(View.VISIBLE);
        homefooter.setVisibility(View.VISIBLE);

       /* findViewById(R.id.cardTrding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frontview.this, TrendingActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });*/
        findViewById(R.id.cardRefMaster).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frontview.this, Referal_Master.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });
        findViewById(R.id.cardWebsite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frontview.this, WebsiteActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        findViewById(R.id.cardLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nf = cn.getActiveNetworkInfo();
                if (nf != null && nf.isConnected() == true) {
                    onLogout();

                } else {
                    Toast.makeText(getApplicationContext(), "Network Connection Not Available", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.ftHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewRegistrationUser.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        findViewById(R.id.ftLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nf = cn.getActiveNetworkInfo();
                if (nf != null && nf.isConnected() == true) {
                    onLogout();
                } else {
                    Toast.makeText(getApplicationContext(), "Network Connection Not Available", Toast.LENGTH_LONG).show();
                }
            }
        });

        String urls = Config.URL + "/api/apilogin.aspx?uid=" + String.valueOf(loginid) + "&pass=" + String.valueOf(passwords) + "&mbl=" + String.valueOf(mobilenumber);
        DownloadWebPageTask task2 = new DownloadWebPageTask();
        task2.execute(new String[]{String.valueOf(urls)});

        Hash_file_maps = new HashMap<String, Integer>();

        sliderLayout = (SliderLayout) findViewById(R.id.slider);

        Hash_file_maps.put("Donation", R.drawable.bg1);
        Hash_file_maps.put("Charity", R.drawable.bg5);
        Hash_file_maps.put(" Donation ", R.drawable.bg6);
        Hash_file_maps.put(" Charity ", R.drawable.bg7);

        for (String name : Hash_file_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(Frontview.this);
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(2000);
        sliderLayout.addOnPageChangeListener(this);
    }

    private void onLogout() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Frontview.this, android.R.style.Theme_DeviceDefault_Dialog);
        alertDialogBuilder.setTitle("Are you sure want to Logout?");
        alertDialogBuilder.setMessage("Click yes to logout!").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                progressBar = ProgressDialog.show(Frontview.this, "Please wait ...", "Logout processing ...", false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                        }
                        session.logoutUser();
                        progressBar.dismiss();
                        Frontview.this.finish();
                    }
                }).start();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Are you sure want to Exit?");
        alertDialogBuilder.setMessage("Click yes to exit!").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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



    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
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

        protected void onPostExecute(String result) {
            s = Html.fromHtml(result).toString();
            if (s.contains("Login Failed")) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(Frontview.this);
                builder2.setTitle("Login");
                builder2.setMessage("LoginId or Mobile No. Not Exist");
                builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        session.logoutUser();
                        finish();
                    }
                });
                builder2.show();
            }
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

     //   Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
