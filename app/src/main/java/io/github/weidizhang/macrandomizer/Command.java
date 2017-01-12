package io.github.weidizhang.macrandomizer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class Command {

    public static String runAsRoot(String command) {
        String[] commands = new String[] { command };
        return runAsRoot(commands);
    }

    public static String runAsRoot(String[] commands) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream outStream = new DataOutputStream(p.getOutputStream());

            for (String command : commands) {
                outStream.writeBytes(command + "\n");
            }

            outStream.writeBytes("exit\n");
            outStream.flush();

            BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String output = "";
            String readLine = null;
            while ((readLine = inStreamReader.readLine()) !=  null) {
                output += readLine + "\n";
            }

            return output.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
