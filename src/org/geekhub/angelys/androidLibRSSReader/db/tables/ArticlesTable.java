package org.geekhub.angelys.androidLibRSSReader.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.geekhub.angelys.androidLibRSSReader.db.FeedsDatabaseHelper;
import org.geekhub.angelys.androidLibRSSReader.objects.Article;
import org.geekhub.angelys.androidLibRSSReader.objects.ArticleCollection;
import org.geekhub.angelys.androidLibRSSReader.utils.Constants;
import org.geekhub.angelys.androidLibRSSReader.utils.RSS;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/17/13
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ArticlesTable {

    // Database table
    public static final String TABLE_ARTICLES = "articles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PUBLISHED_AT = "published_at";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LINK = "link";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ARTICLES
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
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(database);
    }

    public static void updateList(Context context)
    {
        SQLiteDatabase database = new FeedsDatabaseHelper(context).getWritableDatabase();

        RSS rss = new RSS(Constants.feed_link);

        ArticleCollection collection = new ArticleCollection(rss.getItems());

        for (Article article : collection) {

            ContentValues cv = new ContentValues();

            cv.put(COLUMN_TITLE, article.getTitle());
            cv.put(COLUMN_PUBLISHED_AT, article.getPublished_at().toString());
            cv.put(COLUMN_DESCRIPTION, article.getDescription());
            cv.put(COLUMN_LINK, article.getLink());

            database.insert(TABLE_ARTICLES, null, cv);
        }

    }

    public static Article findOneById(Context context, String where, String[] args)
    {
        SQLiteDatabase database = new FeedsDatabaseHelper(context).getWritableDatabase();

        Cursor cursor = database.query(TABLE_ARTICLES, new String[]{COLUMN_ID ,COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PUBLISHED_AT, COLUMN_LINK}, where, args, null, null, null);

        Article article = new Article();
        article.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        article.setPublished_at(new Date(cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHED_AT))));
        article.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        article.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
        article.setLink(cursor.getString(cursor.getColumnIndex(COLUMN_LINK)));

        return article;

    }
}
