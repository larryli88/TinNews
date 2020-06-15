package com.larry.tinnews.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.larry.tinnews.model.NewsResponse;
import com.larry.tinnews.network.NewsApi;
import com.larry.tinnews.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* News DataSource */
public class NewsRepository {

    private final NewsApi newsApi;

    public NewsRepository(Context context) {
        newsApi = RetrofitClient.newsInstance(context).create(NewsApi.class);
    }

    public LiveData<NewsResponse> getTopHeadLines(String country) {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();

        newsApi.getTopHeadlines(country)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if(response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                        } else {
                            topHeadlinesLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });
        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40)
                .enqueue(
                        new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                if(response.isSuccessful()) {
                                    everyThingLiveData.setValue(response.body());
                                } else {
                                    everyThingLiveData.setValue(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        }
                );
        return everyThingLiveData;
    }
}
