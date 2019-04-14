package com.mynytimes.priyanka.mynytimes.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Priyanka on 4/13/2019.
 */

public class TopNewsResponse {

    @SerializedName("num_results")
    private String num_results;

    @SerializedName("results")
    private List<NewsItem> newsItemList;

    public TopNewsResponse() {
    }

    public String getNum_results() {
        return num_results;
    }

    public void setNum_results(String num_results) {
        this.num_results = num_results;
    }

    public List<NewsItem> getNewsItemList() {
        return newsItemList;
    }

    public void setNewsItemList(List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }
}
