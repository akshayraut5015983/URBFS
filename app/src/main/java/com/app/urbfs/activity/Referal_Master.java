package com.app.urbfs.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.app.urbfs.R;
import com.app.urbfs.config.Config;
import com.app.urbfs.config.SessionManager;
import com.google.android.material.navigation.NavigationView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Referal_Master extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    ImageView registration, topup, workingwallet, incomeewallet, newsupdate, teams, direct, incomereport, refer, pinmaster, home;
    ProgressDialog progressBar;
    SessionManager session;
    SharedPreferences pref;
    String tokens, loginid, mobilenumber, passwords, token, s;
    ImageView homef, updatef, logoutf, profilef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referal__master);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        session = new SessionManager(getApplicationContext());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            registration = (ImageView) findViewById(R.id.imgview_regi);
            topup = (ImageView) findViewById(R.id.imgview_topup);
            workingwallet = (ImageView) findViewById(R.id.imgview_workingwallet);
            incomereport = (ImageView) findViewById(R.id.img_incomereport);
            incomeewallet = (ImageView) findViewById(R.id.imgview_wallet);
            newsupdate = (ImageView) findViewById(R.id.imgview_newsandupdates);
            direct = (ImageView) findViewById(R.id.imgview_direct);
            teams = (ImageView) findViewById(R.id.imgview_userteam);
            refer = (ImageView) findViewById(R.id.imgview_refer);
            pinmaster = (ImageView) findViewById(R.id.imgview_pinmaster);

//            home = (ImageView) findViewById(R.id.homeicon);
//            home.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(Referal_Master.this, Frontview.class);
//                    startActivity(intent);
//                }
//            });
            findViewById(R.id.cardRegi).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), NewRegistrationUser.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            });
            findViewById(R.id.cardRefLink).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ReferActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            });
            findViewById(R.id.cardTeam).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Team_main.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            });
            findViewById(R.id.cardDairect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), DirectActivitys.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            });
            findViewById(R.id.cardIncomWalllet).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Referal_Master.this, Income_wallet.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            });

            findViewById(R.id.cardIncomeReport).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Referal_Master.this, Income_report.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            });

            findViewById(R.id.cardPinMaster).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Pinmaster.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            });
            findViewById(R.id.cardNewUpdate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), NewsAndUpdateActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            });

            //Footer
            homef = (ImageView) findViewById(R.id.home_footer);
            homef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Referal_Master.this, Frontview.class);
                    startActivity(intent);
                }
            });
            updatef = (ImageView) findViewById(R.id.update_footer);
            updatef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Referal_Master.this, NewsAndUpdateActivity.class);
                    startActivity(intent);

                }
            });
            logoutf = (ImageView) findViewById(R.id.logout_footer);
            logoutf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo nf = cn.getActiveNetworkInfo();
                    if (nf != null && nf.isConnected() == true) {
                        progressBar = ProgressDialog.show(Referal_Master.this, "Please wait ...", "Logout processing ...", false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (Exception e) {
                                }
                                session.logoutUser();
                                progressBar.dismiss();
                                Referal_Master.this.finish();
                            }
                        }).start();

                    } else {
                        Toast.makeText(getApplicationContext(), "Network Connection Not Available", Toast.LENGTH_LONG).show();
                    }
                }
            });
            profilef = (ImageView) findViewById(R.id.profile_footer);
           /* profilef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Referal_Master.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });*/

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Intent intent = new Intent(Referal_Master.this, Frontview.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.referal__master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.menu_item:
                Intent in = new Intent(Referal_Master.this, Frontview.class);
                startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }

    private String getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        String currentVersion = pInfo.versionName;
        return currentVersion;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_news) {
            Intent inten = new Intent(Referal_Master.this, NewsAndUpdateActivity.class);
            startActivity(inten);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else if (id == R.id.nav_profile) {
            Intent inten = new Intent(Referal_Master.this, ProfileUpdateActivity.class);
            startActivity(inten);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else if (id == R.id.nav_incomewallet) {
            Intent intent = new Intent(Referal_Master.this, Income_wallet.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else if (id == R.id.nav_team) {
            Intent inten = new Intent(Referal_Master.this, Team_main.class);
            startActivity(inten);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (id == R.id.nav_binaryincome) {
            Intent inten = new Intent(Referal_Master.this, DirectActivitys.class);
            startActivity(inten);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (id == R.id.nav_incomehistory) {
            Intent inten = new Intent(Referal_Master.this, Income_report.class);
            startActivity(inten);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (id == R.id.nav_join) {
            Intent inten = new Intent(Referal_Master.this, NewRegistrationUser.class);
            startActivity(inten);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (id == R.id.nav_refer) {
            Intent inten = new Intent(Referal_Master.this, ReferActivity.class);
            startActivity(inten);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else if (id == R.id.nav_logout) {
            onLogout();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onLogout() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Referal_Master.this, android.R.style.Theme_DeviceDefault_Dialog);
        alertDialogBuilder.setTitle("Are you sure want to Logout?");
        alertDialogBuilder.setMessage("Click yes to logout!").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                progressBar = ProgressDialog.show(Referal_Master.this, "Please wait ...", "Logout processing ...", false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                        }
                        session.logoutUser();
                        progressBar.dismiss();
                        Referal_Master.this.finish();
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
                AlertDialog.Builder builder2 = new AlertDialog.Builder(Referal_Master.this);
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
}
