package net.hafiznaufalr.newsapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import net.hafiznaufalr.newsapp.Interface.NewsServices;
import net.hafiznaufalr.newsapp.adapter.ListSourceAdapter;
import net.hafiznaufalr.newsapp.common.Common;
import net.hafiznaufalr.newsapp.model.WebSite;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsServices mService;
    ListSourceAdapter adapter;
    SpotsDialog dialog;
    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init cache
        Paper.init(this);
        //init service
        mService = Common.getNewsServices();

        //init view
        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });

        listWebsite = (RecyclerView)findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebsiteSource(false);


    }

    private void loadWebsiteSource(boolean isRefreshed) {
        if(!isRefreshed)
        {
            String cache = Paper.book().read("cache");
            if(cache != null && !cache.isEmpty() && !cache.equals("null"))//punya cache
            {
                WebSite website = new Gson().fromJson(cache,WebSite.class);//convert cache json  to object
                adapter = new ListSourceAdapter(getBaseContext(),website);
                listWebsite.setAdapter(adapter);
            }
            else//gapunya cache
            {
                dialog.show();
                //fetch cache baru
                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(),response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);

                        //simpan ke cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });
            }
        }
        else //swipe refresh
        {

            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter = new ListSourceAdapter(getBaseContext(),response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //simpan ke cache
                    Paper.book().write("cache", new Gson().toJson(response.body()));

                    //dismiss refresh
                    swipeLayout.setRefreshing(false);

                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });
        }
    }
}
