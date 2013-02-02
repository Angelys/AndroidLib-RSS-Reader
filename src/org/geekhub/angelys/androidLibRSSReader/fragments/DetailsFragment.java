package org.geekhub.angelys.androidLibRSSReader.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import org.geekhub.angelys.R;
import org.geekhub.angelys.androidLibRSSReader.objects.Article;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/2/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DetailsFragment extends Fragment {

    public static DetailsFragment Instance;

    private Article article;

    public DetailsFragment(){}

    public DetailsFragment(Article article){
        this.article = article;
    }

    public void setData(Article article)
    {
        this.article = article;
        setView();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater,container, savedInstanceState );

        if(savedInstanceState != null)
        {
            this.article = new Article();
            article.setTitle((String) savedInstanceState.get("title"));
            article.setDescription((String) savedInstanceState.get("description"));
        }

        return inflater.inflate(R.layout.details, container, false);
    }

    public void onViewCreated(View view, Bundle bundle)
    {
        setHasOptionsMenu(true);
        setView();
        Instance = this;
    }

    public void setView()
    {
        if(article != null && getView() != null)
        {
            TextView title = (TextView)getView().findViewById(R.id.descriprion_title);
            WebView description = (WebView)getView().findViewById(R.id.descriprion);
            description.getSettings().setJavaScriptEnabled(true);
            description.getSettings().setPluginsEnabled(true);
            description.setOverScrollMode(View.OVER_SCROLL_NEVER);

            title.setText(article.getTitle());
            description.loadData(article.getDescription(), "text/html", null);
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        Instance = null;
    }

}
