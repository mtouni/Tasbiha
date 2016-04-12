package com.mal.amr.tasbiha.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.mal.amr.tasbiha.R;
import com.mal.amr.tasbiha.db.Contract;
import com.mal.amr.tasbiha.db.DBHelper;

import java.util.Calendar;

/**
 * Created by Amr on 4/5/2016.
 */
public class Ehsaa extends Fragment {

    CalendarView calendarView;
    Calendar calendar = Calendar.getInstance();
    String whereArg = calendar.get(Calendar.DAY_OF_MONTH)
            + "/" + calendar.get(Calendar.MONTH)
            + "/" + calendar.get(Calendar.YEAR);
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;
    SQLiteDatabase db;
    int[] num = new int[] {0, 0, 0, 0};
    int sum = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.ehsaa_layout, container, false);

        db = new DBHelper(getActivity()).getWritableDatabase();

        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);

        calendarView = (CalendarView) v.findViewById(R.id.calendarView);
        calendarView.setShowWeekNumber(false);

        calendarView.setFirstDayOfWeek(1);

        Calendar minCalndarViewLimit = Calendar.getInstance();
        minCalndarViewLimit.set(Calendar.SECOND, 0);
        minCalndarViewLimit.set(Calendar.MINUTE, 0);
        minCalndarViewLimit.set(Calendar.HOUR, 0);
        minCalndarViewLimit.set(Calendar.DAY_OF_MONTH, minCalndarViewLimit.getActualMinimum(Calendar.DAY_OF_MONTH));
        minCalndarViewLimit.set(Calendar.MONTH, minCalndarViewLimit.get(Calendar.MONTH));

        Calendar maxCalndarViewLimit = Calendar.getInstance();
        maxCalndarViewLimit.set(Calendar.SECOND, 0);
        maxCalndarViewLimit.set(Calendar.MINUTE, 0);
        maxCalndarViewLimit.set(Calendar.HOUR, 0);
        maxCalndarViewLimit.set(Calendar.DAY_OF_MONTH, maxCalndarViewLimit.getActualMaximum(Calendar.DAY_OF_MONTH));
        maxCalndarViewLimit.set(Calendar.MONTH, maxCalndarViewLimit.get(Calendar.MONTH));
        maxCalndarViewLimit.set(Calendar.YEAR, maxCalndarViewLimit.get(Calendar.YEAR));


        calendarView.setMinDate(minCalndarViewLimit.getTimeInMillis());
        calendarView.setMaxDate(maxCalndarViewLimit.getTimeInMillis());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                sum = 0;

                Cursor cursor = db.query(Contract.Tasbiha.TABLE_NAME,
                        new String[]{Contract.FREE_TASBIH,
                                Contract.SOBHAN_ALLAH,
                                Contract.ALHAMDULELLAH,
                                Contract.ALLAH_AKBAR},
                        Contract.DATE_TASBIH + " = ?",
                        new String[]{dayOfMonth + "/" + (month + 1) + "/" + year},
                        null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        num[0] = cursor.getInt(cursor.getColumnIndex(Contract.SOBHAN_ALLAH));
                        num[1] = cursor.getInt(cursor.getColumnIndex(Contract.ALHAMDULELLAH));
                        num[2] = cursor.getInt(cursor.getColumnIndex(Contract.ALLAH_AKBAR));
                        num[3] = cursor.getInt(cursor.getColumnIndex(Contract.FREE_TASBIH));
                    }while (cursor.moveToNext());

                    for (int i : num) {
                        sum += i;
                    }
                }

                snackbar = Snackbar.make(coordinatorLayout, String.valueOf(sum), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snackbar.dismiss();
                            }
                        });
                snackbar.show();

                cursor.close();
            }
        });
        return v;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.ehsaa_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.up_or_down) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            long millitTime = calendar.getTimeInMillis();
            calendarView.setDate(millitTime);

        }

        return false;
    }
}
