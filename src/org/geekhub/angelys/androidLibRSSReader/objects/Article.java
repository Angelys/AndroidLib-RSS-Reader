package org.geekhub.angelys.androidLibRSSReader.objects;

import org.horrabin.horrorss.RssItemBean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/2/13
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Article {

    private String title;

    private Date published_at;

    private String description;

    private String category;

    private String link;

    private String author;

    public Article(){}

    public Article(RssItemBean item){

        this.title = item.getTitle();
        this.published_at = item.getPubDate();
        this.description = item.getDescription();
        this.category = item.getCategory();
        this.link = item.getLink();
        this.author = item.getAuthor();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
