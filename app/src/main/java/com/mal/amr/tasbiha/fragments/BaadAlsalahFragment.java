package com.mal.amr.tasbiha.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mal.amr.tasbiha.R;
import com.mal.amr.tasbiha.adapters.BaadAlsalahAdapter;

/**
 * Created by Amr on 4/6/2016.
 */
public class BaadAlsalahFragment extends Fragment {

    RecyclerView recyclerView;
    String[] azkar_list = new String[4];
    int[] num_list = new int[4];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_baad_alsalah, container, false);

        azkar_list = getActivity().getResources().getStringArray(R.array.azkar_list);
        num_list = getActivity().getResources().getIntArray(R.array.num_list);

        recyclerView = (RecyclerView) v.findViewById(R.id.baad_alsalah_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new BaadAlsalahAdapter(getActivity(), azkar_list, num_list));
        
        for (int i : num_list) {
            Log.d("i", i+"");
        }

        return v;
    }
}
