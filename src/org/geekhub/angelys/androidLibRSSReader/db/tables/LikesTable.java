package org.geekhub.angelys.androidLibRSSReader.db.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/17/13
 * Time: 6:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class LikesTable {

    // Database table
    public static final String TABLE_LIKES = "likes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PUBLISHED_AT = "published_at";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LINK = "link";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LIKES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_PUBLISHED_AT + " text not null,"
            + COLUMN_DESCRIPTION + " text not null,"
            + COLUMN_LINK + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(ArticlesTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKES);
        onCreate(database);
    }
}
