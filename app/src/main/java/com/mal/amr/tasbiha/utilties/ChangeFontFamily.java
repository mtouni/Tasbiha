package com.mal.amr.tasbiha.utilties;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Amr on 4/6/2016.
 */
public class ChangeFontFamily {

    public static void changeTabFontFamily(Context context, TabLayout tabLayout) {
        ViewGroup viewGroup = (ViewGroup) tabLayout.getChildAt(0);
        for (int j = 0; j < viewGroup.getChildCount(); j++) {
            ViewGroup vgTab = (ViewGroup) viewGroup.getChildAt(j);
            int tabChildCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(CustomFontLoader.getCustomFont(context));
                }
            }
        }
    }

    public static void applyFontForToolbarTitle(Activity context, Toolbar toolbar){
        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);
            if(view instanceof TextView){
                TextView tv = (TextView) view;
                if(tv.getText().equals(context.getTitle())){
                    tv.setTypeface(CustomFontLoader.getCustomFont(context));
                    break;
                }
            }
        }
    }

}
