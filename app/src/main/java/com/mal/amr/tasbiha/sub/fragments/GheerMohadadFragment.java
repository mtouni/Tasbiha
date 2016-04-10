package com.mal.amr.tasbiha.sub.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mal.amr.tasbiha.R;

/**
 * Created by Amr on 4/6/2016.
 */
public class GheerMohadadFragment extends Fragment {

    FrameLayout frameLayout;
    TextView count;
    int mCount;

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
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.reset_tasbih, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset2) {
            mCount = 0;
            count.setText(String.valueOf(mCount));
        }
        return super.onOptionsItemSelected(item);
    }
}
