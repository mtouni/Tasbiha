package com.mal.amr.tasbiha.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
    SharedPreferences sh;
    private int i;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tasbih_layout, container, false);

        sh = getActivity().getSharedPreferences("myFrsMenus", Context.MODE_PRIVATE);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        i = sh.getInt("f", 0);

        switch (i) {
            case 0:
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.container, new BaadAlsalahFragment())
                        .commit();
                break;

            case 1:
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.container, new GheerMohadadFragment())
                        .commit();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.tasbih_menu, menu);

        if (selected_item != 0) {
            menu.findItem(selected_item).setChecked(true);
        } else {

            switch (i)  {
                case 0:
                    menu.findItem(R.id.baad_alsalah).setChecked(true);
                    break;

                case 1:
                    menu.findItem(R.id.gheer_mohadad).setChecked(true);
                    break;
            }
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences.Editor editor = sh.edit();

        switch (item.getItemId()) {
            case R.id.baad_alsalah:
                editor.putInt("f", 0);
                editor.apply();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new BaadAlsalahFragment()).commit();
                item.setChecked(true);
                selected_item = item.getItemId();
                break;

            case R.id.gheer_mohadad:
                editor.putInt("f", 1);
                editor.apply();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new GheerMohadadFragment()).commit();
                item.setChecked(true);
                selected_item = item.getItemId();
                break;
        }

        return false;
    }
}
