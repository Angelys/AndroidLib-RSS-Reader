package org.geekhub.angelys.androidLibRSSReader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ListFragment extends BaseListFragment {

    public void onViewCreated(View view, Bundle bundle){

        if(this.data == null)
        {
            getData();
        } else {
            updateUI();
        }

        super.onViewCreated(view, bundle);

    }

    public void getData()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                RSS rss = new RSS(Constants.feed_link);

                data = new ArticleCollection(rss.getItems());

                updateUI();
            }
        }).start();
    }

}
