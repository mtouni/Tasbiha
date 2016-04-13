package com.mal.amr.tasbiha;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
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
    int zekr, currentTempCount, restSum;
    String nameInDB;

    Calendar calendar = Calendar.getInstance();
    String whereArg = calendar.get(Calendar.DAY_OF_MONTH)
            + "/" + (calendar.get(Calendar.MONTH) + 1)
            + "/" + calendar.get(Calendar.YEAR);

    //the current counter
    // which help me in storing in the db,
    // and it's zero when we start the app and it will be zero after using reset menu
    //and there is no affect on it
    //we just use it for get the current counting since we start the app
    int mCounter;

    //the old count after reset to zero
    int exactNum;

    //currentTempCount variable >>
    // the exact count to be displayed in the text view
    //and it gets an exact number from the db in starting the app to be displayed,
    // and it will be zero in using the reset menu

    boolean canBePressed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        db = new DBHelper(this).getWritableDatabase();
        intent = getIntent();
        zekr = intent.getExtras().getInt("zekr");
        currentTempCount = intent.getExtras().getInt("num");
        restSum = intent.getExtras().getInt("sum");
        nameInDB = intent.getExtras().getString("nameInDB");

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
                switch (nameInDB) {
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

                if (canBePressed) {
                    mCounter += 1;
                    currentTempCount += 1;

                    count.setText(String.valueOf(currentTempCount));
                }

                if (currentTempCount == 33) {

                    canBePressed = false;

                    if ((currentTempCount + restSum) == 99) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CounterActivity.this, R.style.AlertDialogTheme);
                        builder.setMessage(R.string.la_elah_ella_allah)
                                .setPositiveButton("تم", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(CounterActivity.this, R.style.AlertDialogTheme);
                        builder.setMessage(R.string.zekr_alert)
                                .setPositiveButton("تم", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ContentValues values = new ContentValues();
                                        values.put(Contract.LA_ELAH_ELLA_ALLAH, (la_elah_ella_allah + 1));
                                        db.update(Contract.Tasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    }

                }
            }
        });

    }

    int la_elah_ella_allah;

    @Override
    protected void onResume() {
        super.onResume();

        String sql = "select * from " + Contract.Tasbiha.TABLE_NAME + " where " + Contract.DATE_TASBIH + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{whereArg});
        if (cursor.moveToFirst()) {
            la_elah_ella_allah = cursor.getInt(cursor.getColumnIndex(Contract.LA_ELAH_ELLA_ALLAH));
        }
        cursor.close();
    }

    @Override
    protected void onPause() {
        super.onPause();

        updateDbInLeaving(nameInDB, currentTempCount, mCounter, exactNum);

    }

    private void updateDbInLeaving(String col, int temp, int c, int n) {
        ContentValues values = new ContentValues();
        values.put(col, temp);
        db.update(Contract.TempTasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});

        ContentValues values1 = new ContentValues();
        values1.put(col, (c + n));
        db.update(Contract.Tasbiha.TABLE_NAME, values1, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});
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
                updateDbByResetMenu(nameInDB, mCounter, exactNum);
                mCounter = 0;
                currentTempCount = 0;
                count.setText(String.valueOf(currentTempCount));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateDbByResetMenu(String col, int c, int n) {
        ContentValues values = new ContentValues();
        values.put(col, 0);
        db.update(Contract.TempTasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});

        ContentValues values1 = new ContentValues();
        values1.put(col, (c + n));
        db.update(Contract.Tasbiha.TABLE_NAME, values1, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});

        exactNum += c;
    }

}
