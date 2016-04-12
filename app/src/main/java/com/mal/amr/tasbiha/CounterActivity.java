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
    int zekr, oldNum, newNum;

    int old = oldNum;
    int dbNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        db = new DBHelper(this).getWritableDatabase();
        intent = getIntent();
        zekr = intent.getExtras().getInt("zekr");
        oldNum = intent.getExtras().getInt("num");

        Log.d("zekr", String.valueOf(zekr));
        Log.d("oldNum", String.valueOf(oldNum));

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

        count.setText(String.valueOf(oldNum));

        newNum = oldNum;

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNum += 1;
                count.setText(String.valueOf(newNum));

                switch (zekr) {
                    case 0:
                        updateDB(Contract.SOBHAN_ALLAH, newNum);
                        break;

                    case 1:
                        updateDB(Contract.ALHAMDULELLAH, newNum);
                        break;

                    case 2:
                        updateDB(Contract.ALLAH_AKBAR, newNum);
                        break;
                }
            }
        });

    }

    public void updateDB(String col, int n) {

        ContentValues values = new ContentValues();
        values.put(col, n);
        db.update(Contract.TempTasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});

//        Cursor cursor = db.query(Contract.TABLE_NAME,
//                new String[]{col},
//                Contract.DATE_TASBIH + " = ?",
//                new String[]{whereArg},
//                null, null, null);

        String sql = "select * from " + Contract.TempTasbiha.TABLE_NAME + " where " + Contract.DATE_TASBIH + " = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{whereArg});

        if (cursor.moveToFirst()) {
            //dbNum = ;

            if (n > oldNum) {


                n = (n - oldNum) + cursor.getInt(cursor.getColumnIndex(col));
            } else {
                n = n + dbNum;
            }
        }

        ContentValues values2 = new ContentValues();
        values2.put(col, n);

        db.update(Contract.Tasbiha.TABLE_NAME, values2, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});
        cursor.close();

        oldNum = n;
        Log.d("oldNum", oldNum + "");
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
                newNum = 0;
                count.setText(String.valueOf(newNum));
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
