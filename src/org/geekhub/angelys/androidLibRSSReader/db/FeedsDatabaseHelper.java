package org.geekhub.angelys.androidLibRSSReader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.geekhub.angelys.androidLibRSSReader.db.tables.ArticlesTable;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/17/13
 * Time: 6:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedsDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "feeds.db";
    private static final int DATABASE_VERSION = 3;

    public FeedsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        ArticlesTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        ArticlesTable.onUpgrade(database, oldVersion, newVersion);
    }

}
