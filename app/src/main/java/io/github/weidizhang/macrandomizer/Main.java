package io.github.weidizhang.macrandomizer;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private Network network = new Network();
    private SharedPreferences sharedPrefs;

    private boolean firstRunListener = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        registerListener();
        doPreReqCheck();
        doFirstRunCheck();

        Button randomizeBtn = (Button) findViewById(R.id.button1);
        randomizeBtn.setOnClickListener(this);

        Button restoreBtn = (Button) findViewById(R.id.button2);
        restoreBtn.setOnClickListener(this);

        updateActualMac();
        updateCurrentMac();
    }

    private void registerListener() {
        Listener listener = new Listener();
        listener.setMain(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(listener, intentFilter);
    }

    private void doPreReqCheck() {
        String getID = Command.runAsRoot("id");
        if (!getID.contains("uid=0")) {
            Toast.makeText(getApplicationContext(), "Error: Root permission not granted, application will not work", Toast.LENGTH_LONG).show();
        }
        else {
            String checkIpBin = Command.runAsRoot("ip -V");

            if (!checkIpBin.contains("ip") && !Command.hasBusybox()) {
                Toast.makeText(getApplicationContext(), "Error: BusyBox not installed and not running custom ROM, please install BusyBox", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void doFirstRunCheck() {
        if (sharedPrefs.getBoolean("firstRun", true)) {
            network.reloadWifi(getApplicationContext());
            firstRunListener = true;
        }
    }

    public void onWifiEnabled() {
        if (firstRunListener) {
            sharedPrefs.edit().putBoolean("firstRun", false).commit();
            sharedPrefs.edit().putString("realMac", network.getCurrentMac()).commit();

            firstRunListener = false;
            updateActualMac();
        }
        else {
            updateCurrentMac();
            Toast.makeText(getApplicationContext(), "Your actual MAC address has been restored", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateActualMac() {
        TextView actualMacText = (TextView) findViewById(R.id.textView2);
        actualMacText.setText(sharedPrefs.getString("realMac", "Loading..."));
    }

    private void updateCurrentMac() {
        TextView currentMacText = (TextView) findViewById(R.id.textView4);
        currentMacText.setText(network.getCurrentMac());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            handleRandomizeButton();
        }
        else if (v.getId() == R.id.button2) {
            handleRestoreButton();
        }
    }

    private void handleRandomizeButton() {
        boolean success = false;

        for (int attempt = 0; attempt < 10; attempt++) {
            String randomMac = network.generateRandomMac();
            network.setMacAddress(randomMac);

            updateCurrentMac();

            if (network.getCurrentMac().equals(randomMac)) {
                success = true;
                Toast.makeText(getApplicationContext(), "Your MAC address has been randomized", Toast.LENGTH_SHORT).show();

                break;
            }
        }

        if (!success) {
            Toast.makeText(getApplicationContext(), "Unable to randomize your MAC address, try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleRestoreButton() {
        network.reloadWifi(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
