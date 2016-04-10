package com.mal.amr.tasbiha;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mal.amr.tasbiha.utilties.ChangeFontFamily;

public class CounterActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout frameLayout;
    TextView count;
    int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ChangeFontFamily.applyFontForToolbarTitle(this, toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.counter);
        count = (TextView) findViewById(R.id.count);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount+=1;
                count.setText(String.valueOf(mCount));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.counter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.reset:
                mCount = 0;
                count.setText(String.valueOf(mCount));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
