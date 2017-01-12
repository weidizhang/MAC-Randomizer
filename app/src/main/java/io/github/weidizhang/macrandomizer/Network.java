package io.github.weidizhang.macrandomizer;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Network {

    public String getInterface() {
        String showCmd = Command.runAsRoot("ip link show");
        if (showCmd.contains("wlan0")) {
            return "wlan0";
        }
        else if (showCmd.contains("eth0")) {
            return "eth0";
        }

        return "error";
    }

    public String getCurrentMac() {
        String interfaceName = getInterface();

        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface interfac : interfaces) {
                if (interfac.getName().equalsIgnoreCase(interfaceName)) {
                    byte[] macBytes = interfac.getHardwareAddress();

                    if (macBytes != null) {
                        StringBuilder strBuilder = new StringBuilder();

                        for (byte b : macBytes) {
                            strBuilder.append(String.format("%02x:", b));
                        }

                        strBuilder.deleteCharAt(strBuilder.length() - 1);
                        return strBuilder.toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return "02:00:00:00:00:00";
    }

    public void setMacAddress(String newAddr) {
        String interfaceName = getInterface();

        String[] commands = new String[] {
            "ip link set " + interfaceName + " address " + newAddr,
            "ip link set " + interfaceName + " broadcast " + newAddr
        };

        Command.runAsRoot(commands);
    }

    public String generateRandomMac() {
        String[] validChars = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f"
        };

        Random r = new Random();
        String result = "";

        for (int i = 0; i < 12; i++) {
            result += validChars[r.nextInt(validChars.length)];

            if (result.length() % 3 == 2 && result.length() < 16) {
                result += ":";
            }
        }

        return result;
    }

    public void reloadWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);
        wifiManager.setWifiEnabled(true);
    }
}
