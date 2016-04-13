package com.mal.amr.tasbiha.sub.fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mal.amr.tasbiha.R;
import com.mal.amr.tasbiha.adapters.BaadAlsalahAdapter;
import com.mal.amr.tasbiha.db.Contract;
import com.mal.amr.tasbiha.db.DBHelper;

import java.util.Calendar;

/**
 * Created by Amr on 4/6/2016.
 */
public class BaadAlsalahFragment extends Fragment {

    //String TAG = "BaadAlsalahFragment";
    RecyclerView recyclerView;
    String[] azkar_list = new String[3];
    int[] num_list = new int[]{0, 0, 0};
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
        View v = inflater.inflate(R.layout.fragment_baad_alsalah, container, false);

        azkar_list = getActivity().getResources().getStringArray(R.array.azkar_list);
        //num_list = getActivity().getResources().getIntArray(R.array.num_list);

        recyclerView = (RecyclerView) v.findViewById(R.id.baad_alsalah_list);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        db = new DBHelper(getActivity()).getWritableDatabase();

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
                num_list[0] = cursor.getInt(cursor.getColumnIndex(Contract.SOBHAN_ALLAH));
                num_list[1] = cursor.getInt(cursor.getColumnIndex(Contract.ALHAMDULELLAH));
                num_list[2] = cursor.getInt(cursor.getColumnIndex(Contract.ALLAH_AKBAR));
            } while (cursor.moveToNext());
        } else {
            ContentValues values = new ContentValues();
            values.put(Contract.FREE_TASBIH, 0);
            values.put(Contract.SOBHAN_ALLAH, 0);
            values.put(Contract.ALHAMDULELLAH, 0);
            values.put(Contract.ALLAH_AKBAR, 0);
            values.put(Contract.LA_ELAH_ELLA_ALLAH, 0);
            values.put(Contract.DATE_TASBIH, whereArg);
            db.insert(Contract.TempTasbiha.TABLE_NAME, null, values);
            db.insert(Contract.Tasbiha.TABLE_NAME, null, values);
        }

        cursor.close();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new BaadAlsalahAdapter(getActivity(), azkar_list, num_list));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset2) {
            recyclerView.setAdapter(new BaadAlsalahAdapter(getActivity(), azkar_list, new int[]{0, 0, 0}));
            ContentValues values = new ContentValues();
            values.put(Contract.SOBHAN_ALLAH, 0);
            values.put(Contract.ALHAMDULELLAH, 0);
            values.put(Contract.ALLAH_AKBAR, 0);
            //values.put(Contract.LA_ELAH_ELLA_ALLAH, 0);
            db.update(Contract.TempTasbiha.TABLE_NAME, values, Contract.DATE_TASBIH + " = ?", new String[] {whereArg});
        }
        return true;
    }
}
