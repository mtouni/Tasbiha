package com.mal.amr.tasbiha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mal.amr.tasbiha.R;

/**
 * Created by Amr on 4/5/2016.
 */
public class Tasbih extends Fragment {


    MenuItem reset;
    int selected_item;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tasbih_layout, container, false);

        //initialize the first fragment
        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.container, new BaadAlsalahFragment())
                .commit();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.tasbih_menu, menu);
        reset = menu.findItem(R.id.reset);

        if (selected_item != 0) {
            menu.findItem(selected_item).setChecked(true);
            if (selected_item == R.id.gheer_mohadad) {
                reset.setVisible(true);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.baad_alsalah:
                item.setChecked(true);
                reset.setVisible(false);
                break;
            case R.id.zekr_hor:
                item.setChecked(true);
                reset.setVisible(false);
                break;
            case R.id.gheer_mohadad:
                item.setChecked(true);
                reset.setVisible(true);
                break;
        }

        selected_item = item.getItemId();
        return true;
    }
}
