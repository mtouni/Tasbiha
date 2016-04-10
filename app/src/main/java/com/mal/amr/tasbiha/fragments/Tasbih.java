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
import com.mal.amr.tasbiha.sub.fragments.BaadAlsalahFragment;
import com.mal.amr.tasbiha.sub.fragments.GheerMohadadFragment;

/**
 * Created by Amr on 4/5/2016.
 */
public class Tasbih extends Fragment {

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

        if (selected_item != 0) {
            menu.findItem(selected_item).setChecked(true);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.baad_alsalah:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new BaadAlsalahFragment()).commit();
                item.setChecked(true);
                selected_item = item.getItemId();
                break;

            case R.id.gheer_mohadad:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new GheerMohadadFragment()).commit();
                item.setChecked(true);
                selected_item = item.getItemId();
                break;
        }

        return false;
    }
}
