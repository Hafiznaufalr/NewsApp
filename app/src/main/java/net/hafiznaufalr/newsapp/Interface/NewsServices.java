package net.hafiznaufalr.newsapp.Interface;

import net.hafiznaufalr.newsapp.model.News;
import net.hafiznaufalr.newsapp.model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsServices {

    @GET("v1/sources?language=en")
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
