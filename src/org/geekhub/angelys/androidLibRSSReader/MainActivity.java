package org.geekhub.angelys.androidLibRSSReader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.geekhub.angelys.androidLibRSSReader.activities.DetailsActivity;
import org.geekhub.angelys.androidLibRSSReader.fragments.BaseListFragment.onListElementSelectedListener;
import org.geekhub.angelys.R;
import org.geekhub.angelys.androidLibRSSReader.fragments.DetailsFragment;
import org.geekhub.angelys.androidLibRSSReader.fragments.ListFragment;
import org.geekhub.angelys.androidLibRSSReader.objects.Article;

public class MainActivity extends SherlockFragmentActivity implements onListElementSelectedListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( getSupportFragmentManager().findFragmentById(R.id.list_fragment) == null)
        {
            ListFragment list_frag = new ListFragment();
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.list_fragment ,list_frag);
            trans.commit();
        }

        setContentView(R.layout.main);
    }

    public void onItemSelected(Article article){

        if(DetailsFragment.Instance != null)
        {
            DetailsFragment.Instance.setData(article);
        } else
        {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("article", article);
            startActivity(intent);
        }
    }
}
