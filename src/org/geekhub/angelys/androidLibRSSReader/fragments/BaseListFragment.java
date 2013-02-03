package org.geekhub.angelys.androidLibRSSReader.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.geekhub.angelys.R;
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
public class BaseListFragment extends ListFragment {

    protected ArticleCollection data;

    public BaseListFragment(){}

    public BaseListFragment(ArticleCollection collection){
        data = collection;
    }

    onListElementSelectedListener mCallBack;

    public interface onListElementSelectedListener
    {
        public void onItemSelected(Article item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater,container, savedInstanceState );

        if(savedInstanceState != null && !savedInstanceState.isEmpty()){
            data = (ArticleCollection)savedInstanceState.getSerializable("collection");
        }

        return inflater.inflate(R.layout.list, container, false);
    }

    public void onSaveInstanceState(Bundle out){
        out.putSerializable("collection", data);
    }

    public void onViewCreated(View view, Bundle bundle)
    {
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mCallBack.onItemSelected(data.get(position));
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

    public void updateUI()
    {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                getListView().setAdapter(new ListAdapter(getActivity(), data));

            }
        });

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);*/
    }

}
