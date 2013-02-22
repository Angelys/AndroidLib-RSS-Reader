package org.geekhub.angelys.androidLibRSSReader.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListFragment;
import org.geekhub.angelys.R;
import org.geekhub.angelys.androidLibRSSReader.db.tables.ArticlesTable;
import org.geekhub.angelys.androidLibRSSReader.objects.ArticleCollection;
import org.geekhub.angelys.androidLibRSSReader.objects.Article;
import org.geekhub.angelys.androidLibRSSReader.utils.ListAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/2/13
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseListFragment extends SherlockListFragment {


    public BaseListFragment(){}

    onListElementSelectedListener mCallBack;

    public interface onListElementSelectedListener
    {
        public void onItemSelected(Article item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);

        super.onCreateView(inflater,container, savedInstanceState );

        return inflater.inflate(R.layout.list, container, false);
    }

    public void onViewCreated(View view, Bundle bundle)
    {
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Cursor cursor = (Cursor)parent.getAdapter().getItem(position);

                mCallBack.onItemSelected(new Article(cursor));
            }
        });
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            mCallBack = (onListElementSelectedListener) activity;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement onListElementSelectedListener interface");
        }
    }

}
