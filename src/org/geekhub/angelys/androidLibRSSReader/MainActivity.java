package org.geekhub.angelys.androidLibRSSReader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.geekhub.angelys.androidLibRSSReader.activities.DetailsActivity;
import org.geekhub.angelys.androidLibRSSReader.fragments.BaseListFragment.onListElementSelectedListener;
import org.geekhub.angelys.R;
import org.geekhub.angelys.androidLibRSSReader.fragments.DetailsFragment;
import org.geekhub.angelys.androidLibRSSReader.objects.Article;

public class MainActivity extends SherlockFragmentActivity implements onListElementSelectedListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
