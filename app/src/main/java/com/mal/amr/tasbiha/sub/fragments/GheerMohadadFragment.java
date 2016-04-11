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
    int mCount;
    SQLiteDatabase db;
    Calendar calendar = Calendar.getInstance();
    String whereArg = calendar.get(Calendar.DAY_OF_MONTH)
            + "/" + (calendar.get(Calendar.MONTH) + 1)
            + "/" + calendar.get(Calendar.YEAR);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gheer_mohadad, container, false);

        frameLayout = (FrameLayout) v.findViewById(R.id.counter);
        count = (TextView) v.findViewById(R.id.count);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount += 1;
                count.setText(String.valueOf(mCount));

                ContentValues values = new ContentValues();
                values.put(Contract.FREE_TASBIH, mCount);
                db.update(Contract.DEMO_TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});
                db.update(Contract.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        db = new DBHelper(getActivity()).getWritableDatabase();

        Cursor cursor = db.query(Contract.DEMO_TABLE_NAME,
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

                mCount = cursor.getInt(cursor.getColumnIndex(Contract.FREE_TASBIH));

            } while (cursor.moveToNext());

        } else {
            ContentValues values = new ContentValues();
            values.put(Contract.DATE_TASBIH, whereArg);
            db.insert(Contract.DEMO_TABLE_NAME, null, values);
            db.insert(Contract.TABLE_NAME, null, values);
        }

        cursor.close();

        count.setText(String.valueOf(mCount));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset2) {
            mCount = 0;
            count.setText(String.valueOf(mCount));

            ContentValues values = new ContentValues();
            values.put(Contract.FREE_TASBIH, 0);
            db.update(Contract.DEMO_TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[]{whereArg});
        }
        return false;
    }
}
