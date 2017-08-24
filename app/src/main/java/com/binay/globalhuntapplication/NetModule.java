package com.binay.globalhuntapplication;

import android.app.Application;
import android.util.Log;

import com.github.simonpercic.oklog3.OkLogInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Binay.
 */
@Module
public class NetModule {
    //Base URL
    private String mBaseUrl;
    //Application
    private Application mApplication;

    // Constructor needs one parameter to instantiate.
    public NetModule(String baseUrl, Application application) {
        this.mBaseUrl = baseUrl;
        this.mApplication = application;
    }

    // for application cache size
    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    //
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        // create an instance of OkLogInterceptor using a builder()
        OkLogInterceptor okLogInterceptor =
                OkLogInterceptor.builder()
                        .setLogInterceptor(url -> {
                            Log.d("API", "" + url);
                            return true;
                        }).withAllLogData().build();
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        client.addInterceptor(mChain -> {
            Request original = mChain.request();
            Request.Builder builder = original.newBuilder().header("Content-Type", "application/json");
            Request request = builder.build();
            return mChain.proceed(request);
        });
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }
}
