package io.github.weidizhang.macrandomizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class Listener extends BroadcastReceiver {

    private Main mainActivity;
    private boolean firstStateChange = true;
    private boolean previousState = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (!firstStateChange) {
            if (previousState == false && wifiManager.isWifiEnabled()) {
                mainActivity.onWifiEnabled();
            }
        }
        else {
            firstStateChange = false;
        }

        previousState = wifiManager.isWifiEnabled();
    }

    public void setMain(Main main) {
        mainActivity = main;
    }
}
