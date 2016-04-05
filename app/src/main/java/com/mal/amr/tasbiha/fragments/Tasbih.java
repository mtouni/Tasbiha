package com.mal.amr.tasbiha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mal.amr.tasbiha.R;

/**
 * Created by Amr on 4/5/2016.
 */
public class Tasbih extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tasbih_layout, container, false);
        return v;
    }
}
