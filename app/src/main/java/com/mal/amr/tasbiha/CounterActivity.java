package com.mal.amr.tasbiha;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mal.amr.tasbiha.db.Contract;
import com.mal.amr.tasbiha.db.DBHelper;
import com.mal.amr.tasbiha.utilties.ChangeFontFamily;

import java.util.Calendar;

public class CounterActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout frameLayout;
    TextView count;
    SQLiteDatabase db;
    Intent intent;
    Calendar calendar = Calendar.getInstance();
    String whereArg = calendar.get(Calendar.DAY_OF_MONTH)
            + "/" + (calendar.get(Calendar.MONTH) + 1)
            + "/" + calendar.get(Calendar.YEAR);
    int zekr;

    //the current counter
    // which help me in storing in the db,
    // and it's zero when we start the app and it will be zero after using reset menu
    //and there is no affect on it
    //we just use it for get the current counting since we start the app
    int mCounter;

    //the old count after reset to zero
    int exactNum;

    //the exact count to be displayed in the text view
    //and it gets an exact number from the db in starting the app to be displayed,
    // and it will be zero in using the reset menu
    int currentTempCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        db = new DBHelper(this).getWritableDatabase();
        intent = getIntent();
        zekr = intent.getExtras().getInt("zekr");
        currentTempCount = intent.getExtras().getInt("num");

        Log.d("zekr", String.valueOf(zekr));
        Log.d("oldNum", String.valueOf(currentTempCount));

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //to change the font style
        ChangeFontFamily.applyFontForToolbarTitle(this, toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.counter);
        count = (TextView) findViewById(R.id.count);

        count.setText(String.valueOf(currentTempCount));

        String sql = "select * from " + Contract.Tasbiha.TABLE_NAME + " where " + Contract.DATE_TASBIH + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{whereArg});
        if (cursor.moveToFirst()) {
            do {
                switch (intent.getExtras().getString("nameInDB")) {
                    case Contract.SOBHAN_ALLAH:
                        exactNum = cursor.getInt(cursor.getColumnIndex(Contract.SOBHAN_ALLAH));
                        Log.d("exactNum", exactNum + "");
                        break;

                    case Contract.ALHAMDULELLAH:
                        exactNum = cursor.getInt(cursor.getColumnIndex(Contract.ALHAMDULELLAH));
                        Log.d("exactNum", exactNum + "");
                        break;

                    case Contract.ALLAH_AKBAR:
                        exactNum = cursor.getInt(cursor.getColumnIndex(Contract.ALLAH_AKBAR));
                        Log.d("exactNum", exactNum + "");
                        break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter += 1;
                currentTempCount += 1;

                count.setText(String.valueOf(currentTempCount));

                switch (zekr) {
                    case 0:
                        updateDB(Contract.SOBHAN_ALLAH, mCounter);
                        break;

                    case 1:
                        updateDB(Contract.ALHAMDULELLAH, mCounter);
                        break;

                    case 2:
                        updateDB(Contract.ALLAH_AKBAR, mCounter);
                        break;
                }
            }
        });

    }

    public void updateDB(String col, int n) {

    }

    @Override
    protected void onResume() {
        super.onResume();
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
                finish();
                break;
            case R.id.reset:
//                newNum = 0;
//                count.setText(String.valueOf(newNum));
                switchZekr(zekr);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchZekr(int z) {
        switch (z) {
            case 0:
                resetZekr(Contract.SOBHAN_ALLAH);
                break;

            case 1:
                resetZekr(Contract.ALHAMDULELLAH);
                break;

            case 2:
                resetZekr(Contract.ALLAH_AKBAR);
                break;
        }
    }

    private void resetZekr(String col) {
        ContentValues values = new ContentValues();
        values.put(col, 0);
        db.update(Contract.TempTasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});
    }
}
