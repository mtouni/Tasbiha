package com.mal.amr.tasbiha;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mal.amr.tasbiha.adapters.MainPagerAdapter;
import com.mal.amr.tasbiha.utilties.ChangeFontFamily;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ChangeFontFamily.applyFontForToolbarTitle(this, toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //to add the name of two tabs
        tabLayout.addTab(tabLayout.newTab().setText("تسبيح"));
        tabLayout.addTab(tabLayout.newTab().setText("إحصاء"));

        //to change their font family
        ChangeFontFamily.changeTabFontFamily(this, tabLayout);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
