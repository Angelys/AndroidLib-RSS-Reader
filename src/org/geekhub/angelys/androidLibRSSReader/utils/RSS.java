package org.geekhub.angelys.androidLibRSSReader.utils;

import android.nfc.Tag;
import android.util.Log;
import org.horrabin.horrorss.RssFeed;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/2/13
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class RSS {

    private String Tag = "RSS";

    private String url;
    private RssParser parser;

    private RssFeed feed;

    public RSS(){
        this.parser = new RssParser();
    }

    public RSS(String url){
        this.url = url;
    }

    public void load()
    {
        try{
            feed = parser.load(url);
        } catch (Exception e){
            //If we couldn't get feed set empty rss
            feed = new RssFeed();
            feed.setItems(new ArrayList<RssItemBean>());
            Log.e(Tag, e.getMessage());
        }
    }

    public List<RssItemBean> getItems(){
        if(feed != null){
            return feed.getItems();
        } else {
            load();
            return feed.getItems();
        }
    }

    public RssFeed getFeed() {
        return feed;
    }

    public void setFeed(RssFeed feed) {
        this.feed = feed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
