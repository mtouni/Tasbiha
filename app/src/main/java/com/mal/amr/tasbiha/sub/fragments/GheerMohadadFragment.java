package com.mal.amr.tasbiha.sub.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mal.amr.tasbiha.R;
import com.mal.amr.tasbiha.db.Contract;
import com.mal.amr.tasbiha.db.DBHelper;

import java.util.Calendar;

/**
 * Created by Amr on 4/6/2016.
 */
public class GheerMohadadFragment extends Fragment {

    //the whole layout to be ppressed
    FrameLayout frameLayout;

    //the textview which display the current count
    TextView count;

    //to deal with the database
    SQLiteDatabase db;
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
    int oldCount;

    //the exact count to be displayed in the text view
    //and it gets an exact number from the db in starting the app to be displayed,
    // and it will be zero in using the reset menu
    int currentTempCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to notify that there is a menu for this fragment
        setHasOptionsMenu(true);

        //to deal with the database
        db = new DBHelper(getActivity()).getWritableDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gheer_mohadad, container, false);

        frameLayout = (FrameLayout) v.findViewById(R.id.counter);
        count = (TextView) v.findViewById(R.id.count);

        //to get the past number from the database
        Cursor cursor = db.query(Contract.Tasbiha.TABLE_NAME,
                new String[]{Contract.FREE_TASBIH},
                Contract.DATE_TASBIH + " = ?",
                new String[]{whereArg},
                null, null, null);

        //to get the past number from the database
        if (cursor.moveToFirst()) {
            oldCount = cursor.getInt(cursor.getColumnIndex(Contract.FREE_TASBIH));
        }
        cursor.close();

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //to increase both of them by 1
                mCounter += 1;
                currentTempCount += 1;

                //to store in the original db
                ContentValues values = new ContentValues();
                //notice that we use ( mCounter + oldCount ) even if the mCount is zero
                values.put(Contract.FREE_TASBIH, (mCounter + oldCount));
                db.update(Contract.Tasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});

                //to store in the temp db
                ContentValues values2 = new ContentValues();
                values2.put(Contract.FREE_TASBIH, currentTempCount);
                db.update(Contract.TempTasbiha.TABLE_NAME, values2, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});

                count.setText(String.valueOf(currentTempCount));
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor cursor = db.query(Contract.TempTasbiha.TABLE_NAME,
                new String[]{Contract.FREE_TASBIH,
                        Contract.SOBHAN_ALLAH,
                        Contract.ALHAMDULELLAH,
                        Contract.ALLAH_AKBAR,
                        Contract.DATE_TASBIH
                },
                Contract.DATE_TASBIH + " = ?",
                new String[]{whereArg},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {

                //to show the exact number in the temp db when starting the app, therefore we use currentTempCount
                currentTempCount = cursor.getInt(cursor.getColumnIndex(Contract.FREE_TASBIH));
                count.setText(String.valueOf(currentTempCount));

            } while (cursor.moveToNext());

        } else {

            //to be modified /////////////////////////////////////////////////////////////////////////////////////////////
            // if there is no such this date in the db, so make a row for this date
            ContentValues values = new ContentValues();
            //****************
            values.put(Contract.SOBHAN_ALLAH, 0);
            values.put(Contract.ALHAMDULELLAH, 0);
            values.put(Contract.ALLAH_AKBAR, 0);
            //****************
            values.put(Contract.DATE_TASBIH, whereArg);

            //insert in the db
            db.insert(Contract.TempTasbiha.TABLE_NAME, null, values);
            db.insert(Contract.Tasbiha.TABLE_NAME, null, values);

            //since this is a new day, so start from zero
            count.setText(String.valueOf(currentTempCount));
        }

        cursor.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset2) {

            //in reset menu, we get the last number from the original db and store it in oldCount variable
            Cursor cursor = db.query(Contract.Tasbiha.TABLE_NAME,
                    new String[]{Contract.FREE_TASBIH},
                    Contract.DATE_TASBIH + " = ?",
                    new String[]{whereArg},
                    null, null, null);

            if (cursor.moveToFirst()) {
                oldCount = cursor.getInt(cursor.getColumnIndex(Contract.FREE_TASBIH));
            }
            cursor.close();

            //to make them both ZERO
            mCounter = 0;
            currentTempCount = 0;

            //since the currentTempCount is for just displaying in text view
            count.setText(String.valueOf(currentTempCount));

            //update the temp db with zero number
            ContentValues values = new ContentValues();
            values.put(Contract.FREE_TASBIH, 0);
            db.update(Contract.TempTasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});
        }
        return true;
    }
}
