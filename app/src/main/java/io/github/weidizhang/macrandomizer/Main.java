package io.github.weidizhang.macrandomizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
            String randomMac = network.generateRandomMac();
            network.setMacAddress(randomMac);

            updateCurrentMac();
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
