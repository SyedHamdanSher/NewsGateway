package com.shertech.newsgateway;

import java.io.Serializable;

/**
 * Created by lastwalker on 4/24/17.
 */

public class NewData implements Serializable {

    private String author;
    private String title;
    private String description;
    private String urlToImage;
    private String publishedAT;
    private String url;
    private String data;
   // private String source;

    public NewData(String author, String title, String description, String urlToImage, String publishedAT,String data,String url) {
        this.author = author;
        this.title = title;
        this.urlToImage = urlToImage;
        this.description = description;
        this.publishedAT = publishedAT;
        this.data = data;
        this.url = url;
        //this.source=source;
    }

    public String getAuthor() {
        return author;
    }
    public String geturl() {
        return url;
    }
    public String getTitle() {
        return title;
    }
    /*public String getSource() {
        return source;
    }*/
    public String getDescription() {
        return description;
    }
    public String getUrlToImage() {
        return urlToImage;
    }
    public String getPublishedAT(){return publishedAT;}
    public String getData(){return data;}

}
