package org.geekhub.angelys.androidLibRSSReader.db.tables;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.geekhub.angelys.androidLibRSSReader.contentProviders.ArticlesContentProvider;
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
    public static final String COLUMN_LIKE = "like";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ARTICLES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_PUBLISHED_AT + " text not null,"
            + COLUMN_DESCRIPTION + " text not null,"
            + COLUMN_LINK + " text not null,"
            + COLUMN_LIKE + " tinyint(1)"
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

    public static int updateList(Context context)
    {
        RSS rss = new RSS(Constants.feed_link);

        ArticleCollection collection = new ArticleCollection(rss.getItems());

        int count = 0;

        for (Article article : collection) {

            ContentValues cv = new ContentValues();

            cv.put(COLUMN_TITLE, article.getTitle());
            cv.put(COLUMN_PUBLISHED_AT, article.getPublished_at().toString());
            cv.put(COLUMN_DESCRIPTION, article.getDescription());
            cv.put(COLUMN_LINK, article.getLink());
            cv.put(COLUMN_LIKE, 0);

            Cursor query = context.getContentResolver().query(ArticlesContentProvider.CONTENT_URI,
                    new String[]{COLUMN_ID ,COLUMN_DESCRIPTION, COLUMN_TITLE},
                    COLUMN_TITLE + " = ?"
                            + " AND " + COLUMN_DESCRIPTION + " = ?",
                    new String[]{article.getTitle(), article.getDescription()},
                    null
            );

            if(query.getCount() == 0)
            {
                context.getContentResolver().insert(ArticlesContentProvider.CONTENT_URI, cv);
                count++;
            }
        }

        return count;
    }

    public static Article findOneBy(Context context, String where, String[] args)
    {
        SQLiteDatabase database = new FeedsDatabaseHelper(context).getWritableDatabase();

        Cursor cursor = database.query(TABLE_ARTICLES, new String[]{COLUMN_ID ,COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PUBLISHED_AT, COLUMN_LINK, COLUMN_LIKE}, where, args, null, null, null);

        Article article = new Article();
        article.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        article.setPublished_at(new Date(cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHED_AT))));
        article.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        article.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
        article.setLink(cursor.getString(cursor.getColumnIndex(COLUMN_LINK)));

        return article;

    }

    public static void saveArticle(Context context, Article article)
    {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, article.getTitle());
        cv.put(COLUMN_PUBLISHED_AT, article.getPublished_at().toString());
        cv.put(COLUMN_LINK, article.getLink());
        cv.put(COLUMN_DESCRIPTION, article.getDescription());
        cv.put(COLUMN_LIKE, article.getLike()?1:0);

        if(article.getId() != 0)
        {
            context.getContentResolver().update(ContentUris.withAppendedId(ArticlesContentProvider.CONTENT_URI, article.getId()), cv, null, null);
        } else
        {
            context.getContentResolver().insert(ArticlesContentProvider.CONTENT_URI, cv);
        }

    }
}
