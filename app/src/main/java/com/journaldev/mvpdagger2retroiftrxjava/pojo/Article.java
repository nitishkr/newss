package com.journaldev.mvpdagger2retroiftrxjava.pojo;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Article
{
    @SerializedName("source")
    public JSONObject source;
    @SerializedName("author")
    public String author;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("url")
    public String url;
    @SerializedName("urlToImage")
    public String urlToImage ;
    @SerializedName("publishedAt")
    public String publishedAt;
    @SerializedName("content")
    public String content ;

}