package com.sandalen.advanceddemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.sandalen.advanceddemo.model.Chapter;
import com.sandalen.advanceddemo.model.ChapterItem;

public class ChapterDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_chapter.db";
    private static final int VERSION = 1;
    private static ChapterDbHelper chapterDbHelper;

    private ChapterDbHelper(@Nullable Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    public static synchronized ChapterDbHelper getInstance(Context context){
        chapterDbHelper = new ChapterDbHelper(context);
        return chapterDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + Chapter.TABLE_NAME + " ("
                        + Chapter.COL_ID + " INTEGER PRIMARY KEY, "
                        + Chapter.COL_NAME + " VARCHAR"
                        + ")"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + ChapterItem.TABLE_NAME + " ("
                        + ChapterItem.COL_ID + " INTEGER PRIMARY KEY, "
                        + ChapterItem.COL_NAME + " VARCHAR, "
                        + ChapterItem.COL_PID + " INTEGER"
                        + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
