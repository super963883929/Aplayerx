package com.tools.aplayer;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.IOException;

public class PlayerInfoUtil {
    private static final String CREAT_POSITION_TABLE = "CREATE TABLE IF NOT EXISTS Player ( id integer PRIMARY KEY,Url text,Position integer)";
    private static SQLiteDatabase db;

    public static void openDataBase(Context context) {
        if (db == null) {
            creatDataBase(context);
            if (new File(getDataBasePath(context) + "/player.db").exists()) {
                db = context.openOrCreateDatabase(getDataBasePath(context) + "/player.db", 0, null);
            }
            craetTable();
        }
    }

    private static void creatDataBase(Context context) {
        File file = new File(getDataBasePath(context) + "/player.db");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void craetTable() {
        if (db != null) {
            db.execSQL(CREAT_POSITION_TABLE);
        }
    }

    private static String getDataBasePath(Context context) {
        String str;
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            str = externalFilesDir.getAbsolutePath() + "/database";
        } else {
            str = context.getFilesDir().getAbsolutePath() + "/database";
        }
        if (!new File(str).exists()) {
            new File(str).mkdirs();
        }
        return str;
    }

    public static void addPlayerInfo(String str, int i) {
        if (db == null) {
            return;
        }
        if (isExistsInfo(str)) {
            db.execSQL("UPDATE Player SET Position = " + i + " WHERE Url = '" + str + "'");
        } else {
            db.execSQL("INSERT INTO Player VALUES (null,'" + str + "'," + i + ")");
        }
    }

    public static boolean isExistsInfo(String str) {
        int i;
        if (db != null) {
            Cursor rawQuery = db.rawQuery("SELECT count(*) FROM Player WHERE Url = '" + str + "'", null);
            if (rawQuery.moveToNext()) {
                i = rawQuery.getInt(0);
            } else {
                i = 0;
            }
            rawQuery.close();
        } else {
            i = 0;
        }
        if (i != 0) {
            return true;
        }
        return false;
    }

    public static int getPosition(String str) {
        int i = 0;
        try {
            if (db != null) {
                Cursor rawQuery = db.rawQuery("SELECT Position FROM Player WHERE Url = '" + str + "'", null);
                if (rawQuery.moveToNext()) {
                    i = rawQuery.getInt(0);
                }
                rawQuery.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static void deleteInfo(String str) {
        if (db != null) {
            db.execSQL("DELETE FROM Player WHERE Url = '" + str + "'");
        }
    }

    public static void clearTable() {
        if (db != null) {
            db.execSQL("delete from Player");
        }
    }

    public static void closeDataBase() {
        if (db != null) {
            db.close();
        }
    }
}