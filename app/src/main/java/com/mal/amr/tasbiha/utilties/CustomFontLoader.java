package com.mal.amr.tasbiha.utilties;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Amr on 4/6/2016.
 */
public class CustomFontLoader {

    private static final int FONTS_NUM = 1;
    private static boolean FONTS_LOADED = false;

    private static Typeface customFont;

    public static void loadFont(Context context) {
        customFont = Typeface.createFromAsset(context.getAssets(), "fonts/stc.otf");

        FONTS_LOADED = true;
    }

    public static Typeface getCustomFont (Context context) {
        if (!FONTS_LOADED) {
            loadFont(context);
        }

        return customFont;
    }
}
