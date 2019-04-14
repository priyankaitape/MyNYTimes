package com.mynytimes.priyanka.mynytimes.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Priyanka on 4/12/2019.
 */

public class NewsItem {

    @SerializedName("title")
    String newsTitle;

    @SerializedName("byline")
    String newsSubtitle;

    @SerializedName("published_date")
    String newsDate;

    @SerializedName("url")
    String newsDetailsURL;

    public NewsItem() {
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsSubtitle() {
        return newsSubtitle;
    }

    public void setNewsSubtitle(String newsSubtitle) {
        this.newsSubtitle = newsSubtitle;
    }

    public String getNewsDetailsURL() {
        return newsDetailsURL;
    }

    public void setNewsDetailsURL(String newsDetailsURL) {
        this.newsDetailsURL = newsDetailsURL;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }


}
