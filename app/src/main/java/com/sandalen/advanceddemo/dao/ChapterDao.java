package com.sandalen.advanceddemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sandalen.advanceddemo.db.ChapterDbHelper;
import com.sandalen.advanceddemo.model.Chapter;
import com.sandalen.advanceddemo.model.ChapterItem;

import java.util.ArrayList;
import java.util.List;

public class ChapterDao {

    public List<Chapter> loadFromDb(Context context){
        ChapterDbHelper helper = ChapterDbHelper.getInstance(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        List<Chapter> chapterList = new ArrayList<>();
        Chapter chapter = null;
        Cursor cursor = database.rawQuery("select * from " + Chapter.TABLE_NAME,null);
        while (cursor.moveToNext()){
            chapter = new Chapter();
            int id = cursor.getInt(cursor.getColumnIndex(Chapter.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(Chapter.COL_NAME));
            chapter.setId(id);
            chapter.setName(name);
            chapterList.add(chapter);
        }

        cursor.close();

        ChapterItem chapterItem = null;
        for (Chapter tmpChapter : chapterList) {
            int pid = tmpChapter.getId();
            cursor = database.rawQuery("select * from " + ChapterItem.TABLE_NAME + " where " + ChapterItem.COL_PID + " = ? ", new String[]{pid + ""});
            while (cursor.moveToNext()) {
                chapterItem = new ChapterItem();
                int id = cursor.getInt(cursor.getColumnIndex(ChapterItem.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(ChapterItem.COL_NAME));
                chapterItem.setId(id);
                chapterItem.setName(name);
                chapterItem.setPid(pid);
                tmpChapter.addChild(id,name);
            }
            cursor.close();
        }

        return chapterList;
    }

    public void insertToDb(Context context,List<Chapter> chapters){
        if(chapters == null || chapters.isEmpty()){
            return;
        }

        ChapterDbHelper helper = ChapterDbHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.beginTransaction();
        ContentValues contentValues = null;
        for(Chapter chapter : chapters) {
            contentValues = new ContentValues();
            contentValues.put(Chapter.COL_ID, chapter.getId());
            contentValues.put(Chapter.COL_NAME, chapter.getName());
            db.insertWithOnConflict(Chapter.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

            List<ChapterItem> chapterItems = chapter.getChildren();
            for (ChapterItem chapterItem : chapterItems) {
                contentValues = new ContentValues();
                contentValues.put(ChapterItem.COL_ID, chapterItem.getId());
                contentValues.put(ChapterItem.COL_NAME, chapterItem.getName());
                contentValues.put(ChapterItem.COL_PID, chapter.getId());
                db.insertWithOnConflict(ChapterItem.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();

    }
}
