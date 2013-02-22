package org.geekhub.angelys.androidLibRSSReader.contentProviders;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import org.geekhub.angelys.androidLibRSSReader.db.FeedsDatabaseHelper;
import org.geekhub.angelys.androidLibRSSReader.db.tables.ArticlesTable;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/17/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArticlesContentProvider extends ContentProvider {

    // database
    private FeedsDatabaseHelper database;

    // Used for the UriMacher
    private static final int ARTICLES = 1;
    private static final int ARTICLE_ID = 2;

    private static final String AUTHORITY = "org.geekhub.angelys.androidLibRSSReader.contentProvider";

    private static final String BASE_PATH = "articles";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/articles";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/article";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, ARTICLES);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ARTICLE_ID);
    }

    @Override
    public boolean onCreate() {
        database = new FeedsDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Check if the caller has requested a column which does not exists
        checkColumns(projection);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ARTICLES:
                // Set the table
                queryBuilder.setTables(ArticlesTable.TABLE_ARTICLES);
                break;
            case ARTICLE_ID:
                // Set the table
                queryBuilder.setTables(ArticlesTable.TABLE_ARTICLES);
                // Adding the ID to the original query
                queryBuilder.appendWhere(ArticlesTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        String base_path = "";
        switch (uriType) {
            case ARTICLES:
                id = sqlDB.insert(ArticlesTable.TABLE_ARTICLES, null, values);
                base_path = BASE_PATH;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(base_path + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        String id = "";
        switch (uriType) {
            case ARTICLES:
                rowsDeleted = sqlDB.delete(ArticlesTable.TABLE_ARTICLES, selection,
                        selectionArgs);
                break;
            case ARTICLE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ArticlesTable.TABLE_ARTICLES,
                            ArticlesTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(ArticlesTable.TABLE_ARTICLES,
                            ArticlesTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        String id = "";
        switch (uriType) {
            case ARTICLES:
                rowsUpdated = sqlDB.update(ArticlesTable.TABLE_ARTICLES,
                        values,
                        selection,
                        selectionArgs);
                break;
            case ARTICLE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ArticlesTable.TABLE_ARTICLES,
                            values,
                            ArticlesTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(ArticlesTable.TABLE_ARTICLES,
                            values,
                            ArticlesTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = { ArticlesTable.COLUMN_TITLE,
                ArticlesTable.COLUMN_DESCRIPTION, ArticlesTable.COLUMN_PUBLISHED_AT, ArticlesTable.COLUMN_LINK,
                ArticlesTable.COLUMN_ID, ArticlesTable.COLUMN_LIKE };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}
