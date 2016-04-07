package com.mal.amr.tasbiha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.mal.amr.tasbiha.R;

/**
 * Created by Amr on 4/5/2016.
 */
public class Ehsaa extends Fragment {

    CalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.ehsaa_layout, container, false);

        CalendarView calendar = (CalendarView) v.findViewById(R.id.calendarView);
        calendar.setShowWeekNumber(true);

        calendar.setFirstDayOfWeek(7);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }


}
