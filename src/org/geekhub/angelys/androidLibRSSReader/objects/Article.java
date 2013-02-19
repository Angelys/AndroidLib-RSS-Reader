package org.geekhub.angelys.androidLibRSSReader.objects;

import android.database.Cursor;
import android.os.Parcelable;
import android.os.Parcel;
import org.geekhub.angelys.androidLibRSSReader.db.tables.ArticlesTable;
import org.horrabin.horrorss.RssItemBean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/2/13
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Article implements Serializable {

    private int Id;

    private String title;

    private Date published_at;

    private String description;

    private String link;

    public Article(){}

    public Article(Cursor cursor)
    {
        Id = cursor.getInt(cursor.getColumnIndex(ArticlesTable.COLUMN_ID));
        title = cursor.getString(cursor.getColumnIndex(ArticlesTable.COLUMN_TITLE));

        try{
            published_at = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(cursor.getString(cursor.getColumnIndex(ArticlesTable.COLUMN_PUBLISHED_AT)));
        }catch (ParseException e) {
            published_at = new Date();
        }
        description = cursor.getString(cursor.getColumnIndex(ArticlesTable.COLUMN_DESCRIPTION));
        link = cursor.getString(cursor.getColumnIndex(ArticlesTable.COLUMN_LINK));
    }

    public Article(RssItemBean item){

        this.title = item.getTitle();
        this.published_at = item.getPubDate();
        this.description = item.getDescription();
        this.link = item.getLink();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublished_at() {
        return published_at;
    }

    public void setPublished_at(Date published_at) {
        this.published_at = published_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
