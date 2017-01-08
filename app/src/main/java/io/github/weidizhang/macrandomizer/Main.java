package io.github.weidizhang.macrandomizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Main extends AppCompatActivity implements View.OnClickListener {

    Network network = new Network();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button randomizeBtn = (Button) findViewById(R.id.button1);
        randomizeBtn.setOnClickListener(this);

        Button restoreBtn = (Button) findViewById(R.id.button2);
        restoreBtn.setOnClickListener(this);

        updateActualMac();
        updateCurrentMac();
    }

    private void updateActualMac() {

    }

    private void updateCurrentMac() {
        TextView currentMacText = (TextView) findViewById(R.id.textView4);
        currentMacText.setText(network.getCurrentMac());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
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
                Toast.makeText(getApplicationContext(), "Unable to randomize your MAC address", Toast.LENGTH_SHORT).show();
            }
        }
        else if (v.getId() == R.id.button2) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
