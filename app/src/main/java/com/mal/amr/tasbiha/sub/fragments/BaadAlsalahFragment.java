package com.mal.amr.tasbiha.sub.fragments;


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
import android.widget.Toast;

import com.mal.amr.tasbiha.R;
import com.mal.amr.tasbiha.adapters.BaadAlsalahAdapter;
import com.mal.amr.tasbiha.db.DBHelper;

import java.util.Calendar;

/**
 * Created by Amr on 4/6/2016.
 */
public class BaadAlsalahFragment extends Fragment {

    RecyclerView recyclerView;
    String[] azkar_list = new String[3];
    int[] num_list = new int[3];
    SQLiteDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_baad_alsalah, container, false);

        db = new DBHelper(getActivity()).getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        String where = calendar.get(Calendar.DAY_OF_MONTH)
                + "/" + (calendar.get(Calendar.MONTH) + 1)
                + "/" + calendar.get(Calendar.YEAR);

        Toast.makeText(getActivity(), where, Toast.LENGTH_LONG).show();
        //Cursor cursor = db.query(Contract.DEMO_TABLE_NAME, )

        azkar_list = getActivity().getResources().getStringArray(R.array.azkar_list);
        num_list = getActivity().getResources().getIntArray(R.array.num_list);

        recyclerView = (RecyclerView) v.findViewById(R.id.baad_alsalah_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new BaadAlsalahAdapter(getActivity(), azkar_list, num_list));

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset2)
            recyclerView.setAdapter(new BaadAlsalahAdapter(getActivity(), azkar_list, new int[] {0, 0, 0}));

        return false;
    }
}
