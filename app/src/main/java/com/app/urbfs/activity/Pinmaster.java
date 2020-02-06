package com.app.urbfs.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.app.urbfs.R;
import com.app.urbfs.config.Config;
import com.app.urbfs.config.SessionManager;
import com.app.urbfs.fragment.pingeneration;

public class Pinmaster extends AppCompatActivity {
    TextView txtclick1, txtclick2, click1, click2;
    ImageView homef, updatef, logoutf, profilef;
    ProgressDialog progressBar;
    SessionManager session;
    SharedPreferences pref;
    String tokens, loginid, mobilenumber, passwords, token, s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinmaster);

        //// Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Logout Session
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
        txtclick1 = (TextView) findViewById(R.id.txtpingeneration);
        txtclick2 = (TextView) findViewById(R.id.pintransfer);
        click1 = (TextView) findViewById(R.id.click1);
        click2 = (TextView) findViewById(R.id.click2);
    /* txtclick2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Pinmaster.this,PinTransferActivity.class);
        startActivity(intent);
        }
        });
        click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Pinmaster.this,PinTransferActivity.class);
                startActivity(intent);
            }
        });*/

        /// Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
        //    R.anim.blink);
        // click1.startAnimation(animation);
        // click2.startAnimation(animation);

        pingeneration fragmentView = new pingeneration();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, fragmentView).commit();

        homef = (ImageView) findViewById(R.id.home_footer);
        homef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pinmaster.this, Frontview.class);
                startActivity(intent);
            }
        });
//        updatef=(ImageView)findViewById(R.id.update_footer);
//        updatef.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Pinmaster.this,NewsUpdateActivity.class);
//                startActivity(intent);
//
//            }
//        });
        logoutf = (ImageView) findViewById(R.id.logout_footer);
        logoutf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nf = cn.getActiveNetworkInfo();
                if (nf != null && nf.isConnected() == true) {
                    progressBar = ProgressDialog.show(Pinmaster.this, "Please wait ...", "Logout processing ...", false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (Exception e) {
                            }
                            session.logoutUser();
                            progressBar.dismiss();
                            Pinmaster.this.finish();
                        }
                    }).start();

                } else {
                    Toast.makeText(getApplicationContext(), "Network Connection Not Available", Toast.LENGTH_LONG).show();
                }
            }
        });
        profilef = (ImageView) findViewById(R.id.profile_footer);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
