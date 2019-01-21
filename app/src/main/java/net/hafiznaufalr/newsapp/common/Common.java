package net.hafiznaufalr.newsapp.common;

import android.support.v4.widget.SwipeRefreshLayout;

import net.hafiznaufalr.newsapp.Interface.IconBetterIdeaService;
import net.hafiznaufalr.newsapp.Interface.NewsServices;
import net.hafiznaufalr.newsapp.model.IconBetterIdea;
import net.hafiznaufalr.newsapp.remote.IconBetterIdeaClient;
import net.hafiznaufalr.newsapp.remote.RetrofitClient;

public class Common {
    private static final String BASE_URL = "https://newsapi.org/";
    public static final String API_KEY = "dcff612c05f24b358a8da5aad0eac289";


    public static NewsServices getNewsServices(){
        return RetrofitClient.getClient(BASE_URL).create(NewsServices.class);
    }

    public static IconBetterIdeaService getIconServices(){
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    public static String getApiUrl(String source,String sortBy, String apiKey){
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v1/articles?source=");
        return apiUrl.append(source)
                .append("&sortBy=")
                .append(sortBy)
                .append("&apiKey=")
                .append(apiKey)
                .toString();
    }
}
