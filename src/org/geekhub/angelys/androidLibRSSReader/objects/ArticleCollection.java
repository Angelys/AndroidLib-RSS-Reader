package org.geekhub.angelys.androidLibRSSReader.objects;

import org.horrabin.horrorss.RssItemBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 2/2/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArticleCollection extends ArrayList<Article> implements Serializable {

    public ArticleCollection(){}

    public ArticleCollection(List<RssItemBean> list){

        for (RssItemBean item : list) {
             this.add(new Article(item));
        }
    }

}
