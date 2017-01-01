package io.github.weidizhang.macrandomizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity implements View.OnClickListener {

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

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {

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
