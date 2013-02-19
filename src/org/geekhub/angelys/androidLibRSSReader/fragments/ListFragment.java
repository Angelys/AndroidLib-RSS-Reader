package org.geekhub.angelys.androidLibRSSReader.fragments;

import android.support.v4.app.LoaderManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.geekhub.angelys.R;
import org.geekhub.angelys.androidLibRSSReader.contentProviders.ArticlesContentProvider;
import org.geekhub.angelys.androidLibRSSReader.db.FeedsDatabaseHelper;
import org.geekhub.angelys.androidLibRSSReader.db.tables.ArticlesTable;
import org.geekhub.angelys.androidLibRSSReader.objects.ArticleCollection;
import org.geekhub.angelys.androidLibRSSReader.utils.Constants;
import org.geekhub.angelys.androidLibRSSReader.utils.RSS;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/2/13
 * Time: 7:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListFragment extends BaseListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleCursorAdapter scAdapter;

    public void onViewCreated(View view, Bundle bundle){

        //Update db
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArticlesTable.updateList(getActivity());
            }
        }).start();


        // открываем подключение к БД
        FeedsDatabaseHelper dbh = new FeedsDatabaseHelper(getActivity());
        db = dbh.getWritableDatabase();

        // получаем курсор
        cursor = db.query(ArticlesTable.TABLE_ARTICLES, null, null, null , null, null, null);
        getActivity().startManagingCursor(cursor);

        // формируем столбцы сопоставления
        String[] from = new String[] { ArticlesTable.COLUMN_TITLE, ArticlesTable.COLUMN_PUBLISHED_AT };
        int[] to = new int[] { R.id.title, R.id.published };

        //Loader init
        getLoaderManager().initLoader(0, null, this);
        // создаем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(getActivity(), R.layout.row_layout, cursor, from, to, 0);
        getListView().setAdapter(scAdapter);

        super.onViewCreated(view, bundle);

    }

    // Creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ArticlesTable.COLUMN_ID,
                ArticlesTable.COLUMN_TITLE,
                ArticlesTable.COLUMN_PUBLISHED_AT,
                ArticlesTable.COLUMN_DESCRIPTION,
                ArticlesTable.COLUMN_LINK
        };
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                ArticlesContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        scAdapter.swapCursor(null);
    }

}
