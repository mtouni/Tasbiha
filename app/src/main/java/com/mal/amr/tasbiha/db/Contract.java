package com.mal.amr.tasbiha.db;

import android.provider.BaseColumns;

/**
 * Created by Amr on 4/8/2016.
 */
public class Contract {
    public static final String FREE_TASBIH = "free_tasbih";
    public static final String SOBHAN_ALLAH = "sobhan_allah";
    public static final String ALHAMDULELLAH = "alhamdulellah";
    public static final String ALLAH_AKBAR = "allah_akbar";
    public static final String DATE_TASBIH = "date_tasbih";

    public static class Tasbiha implements BaseColumns {
        public static final String TABLE_NAME = "tasbihah";
    }

    public static class TempTasbiha implements BaseColumns {
        public static final String TABLE_NAME = "demotasbihah";
    }
}
