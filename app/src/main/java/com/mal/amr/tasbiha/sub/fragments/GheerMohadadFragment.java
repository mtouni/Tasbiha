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

    FrameLayout frameLayout;
    TextView count;
    int mCounter;
    SQLiteDatabase db;
    Calendar calendar = Calendar.getInstance();
    String whereArg = calendar.get(Calendar.DAY_OF_MONTH)
            + "/" + (calendar.get(Calendar.MONTH) + 1)
            + "/" + calendar.get(Calendar.YEAR);

    int oldCount;
    int currentCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        db = new DBHelper(getActivity()).getWritableDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gheer_mohadad, container, false);

        frameLayout = (FrameLayout) v.findViewById(R.id.counter);
        count = (TextView) v.findViewById(R.id.count);

        Cursor cursor = db.query(Contract.Tasbiha.TABLE_NAME,
                new String[]{Contract.FREE_TASBIH},
                Contract.DATE_TASBIH + " = ?",
                new String[]{whereArg},
                null, null, null);

        if (cursor.moveToFirst()) {
            oldCount = cursor.getInt(cursor.getColumnIndex(Contract.FREE_TASBIH));
        }
        cursor.close();

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCounter += 1;
                currentCount += 1;

                ContentValues values = new ContentValues();

                values.put(Contract.FREE_TASBIH, (mCounter + oldCount));
                db.update(Contract.Tasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});

                ContentValues values2 = new ContentValues();
                values2.put(Contract.FREE_TASBIH, currentCount);
                db.update(Contract.TempTasbiha.TABLE_NAME, values2, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});

                count.setText(String.valueOf(currentCount));
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

                currentCount = cursor.getInt(cursor.getColumnIndex(Contract.FREE_TASBIH));
                count.setText(String.valueOf(currentCount));

            } while (cursor.moveToNext());

        } else {
            ContentValues values = new ContentValues();
            values.put(Contract.DATE_TASBIH, whereArg);
            db.insert(Contract.TempTasbiha.TABLE_NAME, null, values);
            db.insert(Contract.Tasbiha.TABLE_NAME, null, values);
        }

        cursor.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset2) {

            Cursor cursor = db.query(Contract.Tasbiha.TABLE_NAME,
                    new String[]{Contract.FREE_TASBIH},
                    Contract.DATE_TASBIH + " = ?",
                    new String[]{whereArg},
                    null, null, null);

            if (cursor.moveToFirst()) {
                oldCount = cursor.getInt(cursor.getColumnIndex(Contract.FREE_TASBIH));
            }
            cursor.close();

            mCounter = 0;
            currentCount = 0;

            count.setText(String.valueOf(currentCount));

            ContentValues values = new ContentValues();
            values.put(Contract.FREE_TASBIH, 0);
            db.update(Contract.TempTasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});
        }
        return true;
    }
}
