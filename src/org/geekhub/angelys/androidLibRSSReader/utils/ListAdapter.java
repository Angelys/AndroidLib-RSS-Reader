package org.geekhub.angelys.androidLibRSSReader.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.geekhub.angelys.R;
import org.geekhub.angelys.androidLibRSSReader.objects.Article;
import org.geekhub.angelys.androidLibRSSReader.objects.ArticleCollection;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/2/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListAdapter extends ArrayAdapter<Article> {

    private Context context;
    private ArticleCollection values;

    public ListAdapter(Context context, ArticleCollection collection){
        super(context, R.layout.row_layout, collection);
        this.context = context;
        this.values = collection;
    }

    public void setData(ArticleCollection data)
    {
        this.values = data;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder viewHolder = null;

        if(convertView == null)
        {
            convertView =  inflater.inflate(R.layout.row_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.published = (TextView) convertView.findViewById(R.id.published);

            convertView.setTag(viewHolder);

        } else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(values.get(position).getTitle());

        String p = new SimpleDateFormat(Constants.date_format).format(values.get(position).getPublished_at());

        viewHolder.published.setText(p);

        return convertView;
    }

    public static class ViewHolder
    {
        TextView title;
        TextView published;
    }

}
